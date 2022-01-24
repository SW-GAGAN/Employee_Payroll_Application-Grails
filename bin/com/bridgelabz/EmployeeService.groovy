package com.bridgelabz

import grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.validation.BindingResult

class EmployeeService {

    def serviceMethod() {}

    /**
     * Purpose : To save the employee if data is valid and if it has no errors with the help of flush
     * if flush is true then it allows ORM to save the employee data
     *
     * @param params
     * @return response
     */
    def save(GrailsParameterMap params) {
        EmployeeModel employee = new EmployeeModel(params)
        def response = AppUtil.saveResponse(false, employee)
        if (employee.validate()) {
            employee.save(flush: true)
            if (!employee.hasErrors()) {
                response.isSuccess = true
            }
        }
        return response
    }

    /**
     * Purpose : To list all the data of the employee in the employee payroll
     * and want to show list to the frontend (pagination)
     * @param params
     * @return
     */
    def list(GrailsParameterMap params) {
        params.max = params.max ?: GlobalConfig.itemsPerPage()
        List<EmployeeModel> employeeList = EmployeeModel.createCriteria().list(params) {
            if (params?.colName && params?.colValue) {
                like(params.colName, "%" + params.colValue + "%")
            }
            if (!params.sort) {
                order("id", "desc")
            }
        } as List<EmployeeModel>
        return [list: employeeList, count: EmployeeModel.count()]
    }

    /**
     * Purpose : To save updated data of employee in database
     *
     * @param employee user whose data needs to be updated
     * @param params  is updated data of employee
     * @return
     */
    def update(EmployeeModel employee, GrailsParameterMap params) {
        employee.properties = params as BindingResult
        def response = AppUtil.saveResponse(false, employee)
        if (employee.validate()) {
            employee.save(flush: true)
            if (!employee.hasErrors()) {
                response.isSuccess = true
            }
        }
        return response
    }

    /**
     * Purpose : To get data of employee with particular ID
     *
     * @param id of the employee whose data needs to be fetched
     * @return data of particular id
     */
    def getById(Serializable id) {
        return EmployeeModel.get(id)
    }

    /**
     * Purpose : To delete data of employee from the database of employee payroll app
     *
     * @param employee is data which needs to be deleted
     * @return
     */
    def delete(EmployeeModel employee) {
        try {
            employee.delete(flush: true)
        } catch (Exception e) {
            println(e.getMessage())
            return false
        }
        return true
    }
}