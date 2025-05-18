package com.bcu.company.controller;

import com.bcu.common.result.Result;
import com.bcu.company.bean.Company;
import com.bcu.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{id}")
    public Result getCompanyById(@PathVariable Integer id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping("/getbycompanyname/{companyName}")
    public Result getCompanyByCompanyName(@PathVariable String companyName) {
        return companyService.getCompanyByCreditCode(companyName);
    }

    @GetMapping
    public Result getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping
    public Result addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping
    public Result updateCompany(@RequestBody Company company) {
        return companyService.updateCompany(company);
    }

    @DeleteMapping("/{id}")
    public Result deleteCompany(@PathVariable Integer id) {
        return companyService.deleteCompany(id);
    }
}