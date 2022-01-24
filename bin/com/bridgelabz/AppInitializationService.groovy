package com.bridgelabz

import grails.gorm.transactions.Transactional

@Transactional
class AppInitializationService {
static initialize() {
        initEmployee()
    }

    private static initEmployee() {
        if(EmployeeModel.count == 0){
            println("No Data Available")
        }
        if (EmployeeModel.count() == 0) {
        
        }
    }
}