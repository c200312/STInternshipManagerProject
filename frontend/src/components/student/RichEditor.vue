buji<template>
  <div class="rich-editor" :class="{ streaming: isStreaming }">
    <!-- 流式输出控制按钮 -->
    <div class="stream-controls" v-if="showStreamControls">
      <button @click="startStream" :disabled="isStreaming" class="stream-btn">
        {{ isStreaming ? '正在接收...' : '开始接收大模型输出' }}
      </button>
      

      <button @click="stopStream" :disabled="!isStreaming" class="stream-btn">
        停止接收
      </button>
      <button @click="clearContent" class="stream-btn clear-btn">
        清空内容
      </button>
    </div>
    
    <!-- Quill编辑器 -->
     <div ref="quillEditor" class="quill-editor"></div>
  </div>
</template>

<script>
import Quill from 'quill'
import MarkdownShortcuts from 'quill-markdown-shortcuts'
import TurndownService from 'turndown'
import showdown from 'showdown'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

// 注册Markdown快捷键插件
Quill.register('modules/markdownShortcuts', MarkdownShortcuts)

export default {
  name: 'RichEditor',
  props: {
    value: {
      type: String,
      default: ''
    },
    showStreamControls: {
      type: Boolean,
      default: true
    },
    streamUrl: {
      type: String,
      default: '/api/ai-stream'
    },
    enableMarkdown: {
      type: Boolean,
      default: true
    },
    defaultOutputFormat: {
      type: String,
      default: 'markdown', // 'html' 或 'markdown'
      validator: value => ['html', 'markdown'].includes(value)
    }
  },
  data() {
    return {
      content: this.value,
      isStreaming: false,
      userIsEditing: false,
      streamSource: null,
      quill: null,
      turndownService: null,
      showdownConverter: null,
      outputFormat: this.defaultOutputFormat
    }
  },
  watch: {
    value(newVal) {
      if (this.quill && newVal !== this.content) {
        // 如果新值是Markdown格式且启用了Markdown
        if (this.enableMarkdown && this.outputFormat === 'markdown' && this.showdownConverter) {
          const html = this.showdownConverter.makeHtml(newVal || '')
          this.quill.root.innerHTML = html
        } else {
          this.quill.root.innerHTML = newVal || ''
        }
        this.content = newVal
      }
    },
    
    // 监听流式状态变化，控制编辑器是否可编辑
    isStreaming: {
      handler(newVal) {
        if (this.quill) {
          this.quill.enable(!newVal)
          
          // 更新占位符文本
          if (newVal) {
            // 进入流式状态时强制重置用户编辑状态
            this.userIsEditing = false
            if (this.editingTimeout) {
              clearTimeout(this.editingTimeout)
              this.editingTimeout = null
            }
            
            // 完全禁用所有事件监听
            this.disableAllEvents()
            
            this.quill.root.setAttribute('data-placeholder', '正在接收流式输出，请稍候...')
          } else {
            // 重新启用事件监听
            this.enableAllEvents()
            
            this.quill.root.setAttribute('data-placeholder', this.placeholder || '请输入内容...')
          }
        }
      },
      immediate: true
    }
  },
  methods: {
    onEditorChange() {
      if (this.quill) {
        const html = this.quill.root.innerHTML
        const text = this.quill.getText()
        
        // 根据输出格式决定内容
        let outputContent = html
        if (this.outputFormat === 'markdown' && this.turndownService) {
          outputContent = this.turndownService.turndown(html)
        }
        
        this.content = outputContent
        this.$emit('input', outputContent)
        this.$emit('change', { 
          html, 
          text, 
          markdown: this.turndownService ? this.turndownService.turndown(html) : ''
        })
        
        // 只有在非流式状态下才标记用户正在编辑
        if (!this.isStreaming) {
          this.userIsEditing = true
          clearTimeout(this.editingTimeout)
          this.editingTimeout = setTimeout(() => {
            this.userIsEditing = false
          }, 1000)
        }
      }
    },
    
    onEditorFocus() {
      // 流式状态下不触发焦点事件
      if (this.isStreaming) {
        return
      }
      this.$emit('focus')
    },
    
    onEditorBlur() {
      // 流式状态下不触发失焦事件
      if (this.isStreaming) {
        return
      }
      this.$emit('blur')
    },
    
    // 禁用所有事件监听
      disableAllEvents() {
        if (this.quill) {
          // 移除所有事件监听器
          this.quill.off('text-change')
          this.quill.off('selection-change')
          
          // 禁用编辑器的所有交互
          this.quill.disable()
          
          // 允许文本选择，但阻止编辑操作
          if (this.quill.root) {
            // 允许文本选择
            this.quill.root.style.userSelect = 'text'
            this.quill.root.style.webkitUserSelect = 'text'
            this.quill.root.style.mozUserSelect = 'text'
            this.quill.root.style.msUserSelect = 'text'
            this.quill.root.style.cursor = 'text'
            
            // 添加事件监听器来阻止编辑相关的交互
            this.preventEventHandler = (e) => {
              // 允许选择相关的事件通过
              if (e.type === 'selectstart' || e.type === 'select' || 
                  (e.type === 'mousedown' && !e.shiftKey && !e.ctrlKey) ||
                  (e.type === 'mousemove' && e.buttons === 1) ||
                  e.type === 'mouseup') {
                return true
              }
              
              // 阻止其他可能影响流式输出的事件
              e.preventDefault()
              e.stopPropagation()
              e.stopImmediatePropagation()
              return false
            }
            
            // 只阻止编辑相关的事件，允许选择事件
            const events = ['click', 'dblclick', 'contextmenu', 
                           'keydown', 'keyup', 'keypress',
                           'touchstart', 'touchend', 'touchmove', 'dragstart', 'drop']
            
            events.forEach(eventType => {
              this.quill.root.addEventListener(eventType, this.preventEventHandler, true)
            })
          }
        }
      },
    
    // 重新启用所有事件监听
      enableAllEvents() {
        if (this.quill) {
          // 重新绑定事件监听器
          this.quill.on('text-change', this.onEditorChange)
          this.quill.on('selection-change', (range) => {
            // 流式状态下不处理选择变化事件
            if (this.isStreaming) {
              return
            }
            
            if (range) {
              this.onEditorFocus()
            } else {
              this.onEditorBlur()
            }
          })
          
          // 启用编辑器
          this.quill.enable()
          
          // 恢复DOM事件和用户选择
          if (this.quill.root) {
            this.quill.root.style.pointerEvents = ''
            this.quill.root.style.userSelect = ''
            this.quill.root.style.webkitUserSelect = ''
            this.quill.root.style.mozUserSelect = ''
            this.quill.root.style.msUserSelect = ''
            this.quill.root.style.cursor = ''
            
            // 移除事件阻止监听器
            if (this.preventEventHandler) {
              const events = ['click', 'dblclick', 'contextmenu', 
                             'keydown', 'keyup', 'keypress',
                             'touchstart', 'touchend', 'touchmove', 'dragstart', 'drop']
              
              events.forEach(eventType => {
                this.quill.root.removeEventListener(eventType, this.preventEventHandler, true)
              })
              
              this.preventEventHandler = null
            }
          }
        }
      },
    
    // 开始接收流式输出
    startStream() {
      if (this.isStreaming) return
      
      this.isStreaming = true
      
      // 模拟流式输出（实际项目中应该连接到真实的AI接口）
      this.simulateStream()
    },
    
    // 停止接收流式输出
    stopStream() {
      this.isStreaming = false
      if (this.streamSource) {
        clearInterval(this.streamSource)
        this.streamSource = null
      }
    },
    
    // 清空内容
     clearContent() {
       if (this.quill) {
         this.quill.setText('')
         this.content = ''
         this.$emit('input', '')
       }
     },
     
     // 模拟流式输出（示例）
     simulateStream() {
       const sampleText = `大模型输出示例\n\n这是一个流式输出的示例。\n\n特点：\n\n1. 实时显示：内容逐步显示\n2. 支持格式：包含各种格式\n3. 可编辑：用户可以随时编辑\n\n代码示例：\nfunction hello() {\n  console.log('Hello World!');\n}\n\n引用内容示例\n\n• 列表项1\n• 列表项2\n• 列表项3\n\n结束`
       
       // 按段落分割文本，避免频繁的全量DOM更新
       const paragraphs = sampleText.split('\n\n')
       let paragraphIndex = 0
       let charIndex = 0
       let currentParagraph = ''
       let lastParagraphElement = null
       
       this.streamSource = setInterval(() => {
         // 严格检查流式状态，确保不会被意外中断
         if (!this.isStreaming || paragraphIndex >= paragraphs.length || !this.quill) {
           this.stopStream()
           return
         }
         
         const paragraph = paragraphs[paragraphIndex]
         
         // 如果当前段落还没有完成
         if (charIndex < paragraph.length) {
           currentParagraph += paragraph[charIndex]
           charIndex++
           
           // 增量更新当前段落，避免全量DOM刷新
           if (this.quill.root) {
             // 如果是新段落的开始，创建新的段落元素
             if (charIndex === 1) {
               lastParagraphElement = document.createElement('p')
               this.quill.root.appendChild(lastParagraphElement)
             }
             
             // 只更新当前段落的内容，不影响其他段落
             if (lastParagraphElement) {
               lastParagraphElement.textContent = currentParagraph
               
               // 自动滚动到底部，但不强制打断用户选择
               if (!window.getSelection().toString()) {
                 this.quill.root.scrollTop = this.quill.root.scrollHeight
               }
             }
           }
         } else {
           // 当前段落完成，移动到下一段落
           paragraphIndex++
           charIndex = 0
           currentParagraph = ''
           
           // 如果还有更多段落，添加段落间距
           if (paragraphIndex < paragraphs.length && this.quill.root) {
             const spacerElement = document.createElement('p')
             spacerElement.innerHTML = '&nbsp;' // 空段落作为间距
             this.quill.root.appendChild(spacerElement)
           }
         }
       }, 50) // 每50ms添加一个字符
     },
    
    // 获取编辑器实例
     getQuill() {
       return this.quill
     },
     
     // 插入内容到光标位置
      insertContent(content) {
        if (this.quill) {
          const range = this.quill.getSelection()
          if (range) {
            this.quill.insertText(range.index, content)
          } else {
            this.quill.insertText(this.quill.getLength(), content)
          }
        }
      },
      
      // 设置Markdown内容
      setMarkdownContent(markdown) {
        if (this.quill && this.showdownConverter) {
          const html = this.showdownConverter.makeHtml(markdown)
          this.quill.root.innerHTML = html
          // 触发内容变化事件
          this.onEditorChange()
        }
      },
      
      // 手动粘贴Markdown内容
      pasteMarkdownContent(markdown) {
        if (!markdown) return
        
        console.log('手动粘贴Markdown内容:', markdown)
        if (this.showdownConverter) {
          const html = this.showdownConverter.makeHtml(markdown)
          console.log('转换后的HTML:', html)
          
          // 清空当前内容并设置新内容
          this.quill.root.innerHTML = html
          this.onEditorChange()
        }
      },
      
      // 获取Markdown内容
      getMarkdownContent() {
        if (this.quill && this.turndownService) {
          const html = this.quill.root.innerHTML
          return this.turndownService.turndown(html)
        }
        return ''
      },
      
      // 获取HTML内容
      getHtmlContent() {
        return this.quill ? this.quill.root.innerHTML : ''
      },
      
      // 切换输出格式
      toggleOutputFormat() {
        this.outputFormat = this.outputFormat === 'html' ? 'markdown' : 'html'
        this.onEditorChange() // 触发内容更新
      },
      
      // 处理粘贴事件
      handlePaste(event) {
        if (!this.enableMarkdown || !this.showdownConverter) return
        
        event.preventDefault()
        const clipboardData = event.clipboardData || window.clipboardData
        const pastedText = clipboardData.getData('text/plain')
        
        // 检测是否为Markdown格式（简单检测）
        const isMarkdown = this.detectMarkdown(pastedText)
        
        if (isMarkdown) {
          console.log('检测到Markdown格式，正在转换...', pastedText)
          const html = this.showdownConverter.makeHtml(pastedText)
          
          // 获取当前光标位置
          const range = this.quill.getSelection()
          if (range) {
            // 删除选中的内容（如果有）
            this.quill.deleteText(range.index, range.length)
            // 插入转换后的HTML
            this.quill.clipboard.dangerouslyPasteHTML(range.index, html)
          } else {
            // 在末尾插入
            this.quill.clipboard.dangerouslyPasteHTML(this.quill.getLength(), html)
          }
        } else {
          // 普通文本，直接插入
          const range = this.quill.getSelection()
          if (range) {
            this.quill.insertText(range.index, pastedText)
          } else {
            this.quill.insertText(this.quill.getLength(), pastedText)
          }
        }
      },
      
      // 检测文本是否为Markdown格式
      detectMarkdown(text) {
        if (!text) return false
        
        // 检测Markdown特征
        const markdownPatterns = [
          /^#{1,6}\s+/m,           // 标题
          /\*\*.*\*\*/,            // 粗体
          /\*.*\*/,               // 斜体
          /!\[.*\]\(.*\)/,        // 图片
          /\[.*\]\(.*\)/,         // 链接
          /^\s*[-*+]\s+/m,        // 无序列表
          /^\s*\d+\.\s+/m,        // 有序列表
          /^\s*>\s+/m,            // 引用
          /```[\s\S]*```/,        // 代码块
          /`.*`/                  // 内联代码
        ]
        
        return markdownPatterns.some(pattern => pattern.test(text))
       },
       
       // 模拟粘贴测试
       pasteTestMarkdown() {
         const testMarkdown = `# 实习系统\n# 项目功能\n![img.png](img.png)\n正在逐步完成各功能\n# 项目环境\n项目基于spring boot+vue开发\n使用mysql+mongodb数据库\n集成\nspring ai框架实现教师评语生成\npoi实现通过模板导出word\n# 使用说明\n\n# 版本内容更新\n###### v1.0.0:`
         this.pasteMarkdownContent(testMarkdown)
       }
   },
   
   mounted() {
      // 初始化Markdown转换器
      this.turndownService = new TurndownService({
        headingStyle: 'atx',
        codeBlockStyle: 'fenced',
        linkStyle: 'inlined',
        linkReferenceStyle: 'full',
        emDelimiter: '*',
        strongDelimiter: '**'
      })
      
      // 保留图片和链接的转换规则
      this.turndownService.addRule('image', {
        filter: 'img',
        replacement: function (content, node) {
          const alt = node.getAttribute('alt') || ''
          const src = node.getAttribute('src') || ''
          const title = node.getAttribute('title')
          const titlePart = title ? ` "${title}"` : ''
          return `![${alt}](${src}${titlePart})`
        }
      })
      
      this.showdownConverter = new showdown.Converter({
        tables: true,
        strikethrough: true,
        tasklists: true,
        simpleLineBreaks: true,
        parseImgDimensions: true,
        simplifiedAutoLink: true,
        excludeTrailingPunctuationFromURLs: true,
        literalMidWordUnderscores: true,
        ghCodeBlocks: true
      })
      
      // 配置Quill模块
      const modules = {
        toolbar: [
          ['bold', 'italic', 'underline', 'strike'],
          [{ 'size': ['small', false, 'large', 'huge'] }],
          [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
          [{ 'color': [] }, { 'background': [] }],
          [{ 'align': [] }],
          ['blockquote', 'code-block'],
          [{ 'list': 'ordered'}, { 'list': 'bullet' }],
          [{ 'indent': '-1'}, { 'indent': '+1' }],
          ['link', 'image'],
          ['clean']
        ]
      }
      
      // 如果启用Markdown，添加快捷键支持
      if (this.enableMarkdown) {
        modules.markdownShortcuts = {}
      }
      
      // 初始化Quill编辑器
      this.quill = new Quill(this.$refs.quillEditor, {
        theme: 'snow',
        placeholder: this.isStreaming ? 
          '正在接收流式输出，请等待完成后再编辑...' :
          (this.enableMarkdown ? 
            '请输入内容或使用Markdown语法（如 # 标题，**粗体**，*斜体*）...' : 
            '请输入内容或点击开始接收大模型输出...'),
        modules,
        readOnly: this.isStreaming // 根据流式状态设置初始只读状态
      })
     
     // 设置初始内容
      if (this.value) {
        if (this.enableMarkdown && this.outputFormat === 'markdown') {
          this.setMarkdownContent(this.value)
        } else {
          this.quill.root.innerHTML = this.value
        }
      }
     
     // 绑定事件（使用enableAllEvents方法统一管理）
      this.enableAllEvents()
      
      // 添加粘贴事件处理
      if (this.enableMarkdown) {
        this.quill.root.addEventListener('paste', this.handlePaste)
      }
   },
   
   beforeUnmount() {
      this.stopStream()
      if (this.editingTimeout) {
        clearTimeout(this.editingTimeout)
      }
      
      // 移除粘贴事件监听器
      if (this.enableMarkdown && this.quill && this.quill.root) {
        this.quill.root.removeEventListener('paste', this.handlePaste)
      }
    }
 }
</script>

<style scoped>
.rich-editor {
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
  font-family: Arial, sans-serif;
}

.stream-controls {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  background-color: #f8f9fa;
  border-bottom: 1px solid #ddd;
  gap: 8px;
  flex-wrap: wrap;
}

.stream-btn {
  padding: 6px 12px;
  border: 1px solid #007bff;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.2s;
}

.stream-btn:hover:not(:disabled) {
  background-color: #0056b3;
  border-color: #0056b3;
}

.stream-btn:disabled {
  background-color: #6c757d;
  border-color: #6c757d;
  cursor: not-allowed;
  opacity: 0.6;
}

.stream-btn.clear-btn {
  background-color: #dc3545;
  border-color: #dc3545;
}

.stream-btn.clear-btn:hover {
  background-color: #c82333;
  border-color: #bd2130;
}



.quill-editor {
  border: none;
  min-height: 200px;
  background: white;
  transition: all 0.3s ease;
}

/* 流式状态下的编辑器样式 */
.quill-editor.ql-disabled {
  background-color: #f8f9fa;
  opacity: 0.8;
  /* 移除pointer-events: none以允许文本选择 */
}

.quill-editor.ql-disabled .ql-editor {
  color: #6c757d;
  cursor: text; /* 允许文本光标显示 */
  user-select: text; /* 允许文本选择 */
}

.quill-editor.ql-disabled .ql-toolbar {
  opacity: 0.5;
  pointer-events: none;
}

/* 流式状态下的额外保护 */
.rich-editor.streaming .quill-editor {
  /* 允许文本选择，但禁用编辑 */
}

.rich-editor.streaming .quill-editor .ql-editor {
  user-select: text !important; /* 允许文本选择 */
  cursor: text !important; /* 显示文本光标 */
}

/* 禁用工具栏交互但保持编辑器文本可选择 */
.rich-editor.streaming .ql-toolbar {
  pointer-events: none !important;
}

/* Quill编辑器样式覆盖 */
:deep(.ql-editor) {
  min-height: 300px;
  font-size: 16px;
  line-height: 1.6;
}

:deep(.ql-toolbar) {
  border-top: none;
  border-left: none;
  border-right: none;
  border-bottom: 1px solid #ccc;
}

:deep(.ql-container) {
  border-left: none;
  border-right: none;
  border-bottom: none;
}

/* 流式输出时的动画效果 */
.streaming-indicator {
  position: relative;
}

.streaming-indicator::after {
  content: '|';
  animation: blink 1s infinite;
  color: #007bff;
}

@keyframes blink {
  0%, 50% { opacity: 1; }
  51%, 100% { opacity: 0; }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .stream-controls {
    flex-direction: column;
    align-items: stretch;
  }
  
  .stream-btn {
    margin-bottom: 4px;
  }
  
  :deep(.ql-toolbar) {
    padding: 4px;
  }
  
  :deep(.ql-editor) {
    min-height: 200px;
    font-size: 14px;
  }
}
</style>