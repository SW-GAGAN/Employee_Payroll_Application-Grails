package com.bridgelabz

class EmployeeController {

 EmployeeService employeeService

    /**
     * Purpose : To collect the data from the service layer i.e collect the list of employee
     *            and return it on UI
     * @return
     */
    def index() {
        def response = employeeService.list(params)
        [employeeList: response.list, total:response.count]
    }

    /**
     * Purpose : To collect data of particular employee with the help of id
     *
     * @param id of the particular employee
     * @return data of that employee
     */
    def details(Integer id) {
        def response = employeeService.getById(id)
        if (!response){
            redirect(controller: "employee", action: "index")
        }else{
            [employee: response]
        }
    }

    /**
     * Purpose : TO redirect to params which will be used in save method
     * @return
     */
    def create(){
        [employee: flash.redirectParams]
    }

    /**
     * Purpose : To save the data if response is valid and it has no errors and redirect to index
     *           if it has errors it will be redirected to create page
     *
     * @return
     */
    def save() {
        def response = employeeService.save(params)
        if (!response.isSuccess) {
            flash.redirectParams = response.model
            redirect(controller: "employee", action: "create")
        }else{
            redirect(controller: "employee", action: "index")
        }
    }

    /**
     * Purpose : To get the data of the employee with the particular id and then redirect to index page
     *
     * @param id whose data that needs to be fetched
     * @return
     */
    def edit(Integer id) {
        if (flash.redirectParams) {
            [employee: flash.redirectParams]
        } else {
            def response = employeeService.getById(id)
            if (!response) {
                flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
                redirect(controller: "employee", action: "index")
            } else {
                [employee: response]
            }
        }
    }

    /**
     * Purpose : To Update the data of the employee with the help of service layer method
     *
     * @return
     */
    def update() {
        def response = employeeService.getById(params.id)
        if (!response){
            flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
            redirect(controller: "employee", action: "index")
        }else{
            response = employeeService.update(response, params)
            if (!response.isSuccess){
                flash.redirectParams = response.model
                flash.message = AppUtil.infoMessage(g.message(code: "unable.to.update"), false)
                redirect(controller: "employee", action: "edit")
            }else{
                flash.message = AppUtil.infoMessage(g.message(code: "updated"))
                redirect(controller: "employee", action: "index")
            }
        }
    }

    /**
     * Purpose : To delete data of employee by using the service layer delete method
     *
     * @param id is employee id of the employee whose data needs to be deleted
     * @return
     */
    def delete(Integer id) {
        def response = employeeService.getById(id)
        if (!response){
            flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
            redirect(controller: "employee", action: "index")
        }else{
            response = employeeService.delete(response)
            if (!response){
                flash.message = AppUtil.infoMessage(g.message(code: "unable.to.delete"), false)
            }else{
                flash.message = AppUtil.infoMessage(g.message(code: "deleted"))
            }
            redirect(controller: "employee", action: "index")
        }
    }
}