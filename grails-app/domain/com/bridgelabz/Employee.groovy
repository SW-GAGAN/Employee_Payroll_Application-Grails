package com.bridgelabz

import grails.rest.Resource
import java.time.LocalDate

@Resource(uri="/employee")
class EmployeeModel {
    Long id
    String name
    String profilePic
    String gender
    String[] department
    Integer salary
    LocalDate startDate
    String note

    static constraints = {
        id unique: true, blank: false
        name size: 4..20, blank: false
        profilePic nullable: true
        gender nullable: true
        department nullable: true
        salary nullable: true, min: 10000
        startDate nullable: true
        note blank: false
    }
}