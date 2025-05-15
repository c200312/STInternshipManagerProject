package com.bcu.company.service;

import com.bcu.common.result.Result;
import com.bcu.company.bean.Company;
import com.bcu.company.bean.CompanyExample;
import com.bcu.company.dao.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyMapper companyMapper;

    public Result getCompanyById(Integer id) {
        Company company = companyMapper.selectByPrimaryKey(id);
        if (company != null) {
            return Result.success(company, "成功获取企业信息");
        } else {
            return Result.error("未找到对应的企业信息");
        }
    }

    public Result getAllCompanies() {
        List<Company> companies = companyMapper.selectByExample(null);
        if (companies.isEmpty()) {
            return Result.error("未找到任何企业信息");
        } else return Result.success(companies, "成功获取所有企业信息");
    }

    public Result addCompany(Company company) {
        if (company == null || company.getCompany_name() == null) {
            return Result.error("企业信息不完整，无法添加");
        } else {
            companyMapper.insertSelective(company);
            return Result.success(company, "企业信息添加成功");
        }
    }

    public Result updateCompany(Company company) {
        if (company == null || company.getC_id() == null) {
            return Result.error("企业ID缺失，无法更新");
        } else {
            companyMapper.updateByPrimaryKeySelective(company);
            return Result.success(company, "企业信息更新成功");
        }
    }

    public Result deleteCompany(Integer id) {
        if (id == null) {
            return Result.error("企业ID缺失，无法删除");
        } else {
            companyMapper.deleteByPrimaryKey(id);
            return Result.success(null, "企业信息删除成功");
        }
    }

    public Result getCompanyByCreditCode(String companyName) {
        CompanyExample example = new CompanyExample();
        example.createCriteria().andCompany_nameEqualTo(companyName);
        List<Company> companies = companyMapper.selectByExample(example);
        if (companies.size() == 1){
            return Result.success(companies.getFirst(), "成功获取企业信息");
        }else return Result.error("未找到对应的企业信息");
    }
}