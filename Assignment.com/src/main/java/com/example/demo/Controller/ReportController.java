package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.ReportService;

@RestController
@RequestMapping("report")
public class ReportController {
	

	    @Autowired
	    private ReportService reportService;

	    @GetMapping("/generate")
	    public String generateReport() {
	        reportService.generateReport();
	        return "Report generated successfully!";
	    }
	}


