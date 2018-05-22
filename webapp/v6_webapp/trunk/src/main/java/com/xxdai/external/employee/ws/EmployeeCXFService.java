package com.xxdai.external.employee.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-08-02T18:11:57.537+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.employee.xxdai.com/", name = "EmployeeCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface EmployeeCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "allocationEmpPermission", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.AllocationEmpPermission")
    @WebMethod
    @ResponseWrapper(localName = "allocationEmpPermissionResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.AllocationEmpPermissionResponse")
    public java.lang.String allocationEmpPermission(
        @WebParam(name = "empInfo", targetNamespace = "")
        java.lang.String empInfo,
        @WebParam(name = "roleId", targetNamespace = "")
        java.lang.String roleId
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getEmployee", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.GetEmployee")
    @WebMethod
    @ResponseWrapper(localName = "getEmployeeResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.GetEmployeeResponse")
    public java.lang.String getEmployee(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryAllEmpIdName", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryAllEmpIdName")
    @WebMethod
    @ResponseWrapper(localName = "queryAllEmpIdNameResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryAllEmpIdNameResponse")
    public java.lang.String queryAllEmpIdName(
        @WebParam(name = "loginId", targetNamespace = "")
        java.lang.String loginId
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryDepartmentById", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryDepartmentById")
    @WebMethod
    @ResponseWrapper(localName = "queryDepartmentByIdResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryDepartmentByIdResponse")
    public java.lang.String queryDepartmentById(
        @WebParam(name = "queryDep", targetNamespace = "")
        java.lang.String queryDep
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryEmployee", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryEmployee")
    @WebMethod
    @ResponseWrapper(localName = "queryEmployeeResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryEmployeeResponse")
    public java.lang.String queryEmployee(
        @WebParam(name = "queryParam", targetNamespace = "")
        java.lang.String queryParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updatePwd", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.UpdatePwd")
    @WebMethod
    @ResponseWrapper(localName = "updatePwdResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.UpdatePwdResponse")
    public java.lang.String updatePwd(
        @WebParam(name = "updateParam", targetNamespace = "")
        java.lang.String updateParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryAllPosition", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryAllPosition")
    @WebMethod
    @ResponseWrapper(localName = "queryAllPositionResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryAllPositionResponse")
    public java.lang.String queryAllPosition(
        @WebParam(name = "positionId", targetNamespace = "")
        java.lang.String positionId
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryPosDepById", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryPosDepById")
    @WebMethod
    @ResponseWrapper(localName = "queryPosDepByIdResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryPosDepByIdResponse")
    public java.lang.String queryPosDepById(
        @WebParam(name = "queryPD", targetNamespace = "")
        java.lang.String queryPD
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryEmployeeById", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryEmployeeById")
    @WebMethod
    @ResponseWrapper(localName = "queryEmployeeByIdResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryEmployeeByIdResponse")
    public java.lang.String queryEmployeeById(
        @WebParam(name = "queryEmp", targetNamespace = "")
        java.lang.String queryEmp
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "creditAppLogin", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.CreditAppLogin")
    @WebMethod
    @ResponseWrapper(localName = "creditAppLoginResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.CreditAppLoginResponse")
    public java.lang.String creditAppLogin(
        @WebParam(name = "jsonString", targetNamespace = "")
        java.lang.String jsonString
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getIsUseToken", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.GetIsUseToken")
    @WebMethod
    @ResponseWrapper(localName = "getIsUseTokenResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.GetIsUseTokenResponse")
    public java.lang.String getIsUseToken(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectSubEmployees", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.SelectSubEmployees")
    @WebMethod
    @ResponseWrapper(localName = "selectSubEmployeesResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.SelectSubEmployeesResponse")
    public java.lang.String selectSubEmployees(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addEmployee", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.AddEmployee")
    @WebMethod
    @ResponseWrapper(localName = "addEmployeeResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.AddEmployeeResponse")
    public java.lang.String addEmployee(
        @WebParam(name = "employeeStr", targetNamespace = "")
        java.lang.String employeeStr,
        @WebParam(name = "roleId", targetNamespace = "")
        java.lang.String roleId
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryAllDept", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryAllDept")
    @WebMethod
    @ResponseWrapper(localName = "queryAllDeptResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryAllDeptResponse")
    public java.lang.String queryAllDept(
        @WebParam(name = "deptId", targetNamespace = "")
        java.lang.String deptId
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateEmpModify", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.UpdateEmpModify")
    @WebMethod
    @ResponseWrapper(localName = "updateEmpModifyResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.UpdateEmpModifyResponse")
    public java.lang.String updateEmpModify(
        @WebParam(name = "jsonString", targetNamespace = "")
        java.lang.String jsonString
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryIdWithName", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryIdWithName")
    @WebMethod
    @ResponseWrapper(localName = "queryIdWithNameResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryIdWithNameResponse")
    public java.lang.String queryIdWithName(
        @WebParam(name = "depName", targetNamespace = "")
        java.lang.String depName
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateEmployeeInfo", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.UpdateEmployeeInfo")
    @WebMethod
    @ResponseWrapper(localName = "updateEmployeeInfoResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.UpdateEmployeeInfoResponse")
    public java.lang.String updateEmployeeInfo(
        @WebParam(name = "lastLogin", targetNamespace = "")
        java.lang.String lastLogin
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryParNo", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryParNo")
    @WebMethod
    @ResponseWrapper(localName = "queryParNoResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryParNoResponse")
    public java.lang.String queryParNo(
        @WebParam(name = "queryParNo", targetNamespace = "")
        java.lang.String queryParNo
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateEmployee", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.UpdateEmployee")
    @WebMethod
    @ResponseWrapper(localName = "updateEmployeeResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.UpdateEmployeeResponse")
    public java.lang.String updateEmployee(
        @WebParam(name = "updateEmployee", targetNamespace = "")
        java.lang.String updateEmployee,
        @WebParam(name = "roleId", targetNamespace = "")
        java.lang.String roleId
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryDeptEmpIdName", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryDeptEmpIdName")
    @WebMethod
    @ResponseWrapper(localName = "queryDeptEmpIdNameResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryDeptEmpIdNameResponse")
    public java.lang.String queryDeptEmpIdName(
        @WebParam(name = "loginId", targetNamespace = "")
        java.lang.String loginId
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryEmployeeByServiceNum", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryEmployeeByServiceNum")
    @WebMethod
    @ResponseWrapper(localName = "queryEmployeeByServiceNumResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryEmployeeByServiceNumResponse")
    public java.lang.String queryEmployeeByServiceNum(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "recordEmp", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.RecordEmp")
    @WebMethod
    @ResponseWrapper(localName = "recordEmpResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.RecordEmpResponse")
    public java.lang.String recordEmp(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "autoJobNum", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.AutoJobNum")
    @WebMethod
    @ResponseWrapper(localName = "autoJobNumResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.AutoJobNumResponse")
    public java.lang.String autoJobNum(
        @WebParam(name = "jobNum", targetNamespace = "")
        java.lang.String jobNum
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryEmpById", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryEmpById")
    @WebMethod
    @ResponseWrapper(localName = "queryEmpByIdResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.QueryEmpByIdResponse")
    public java.lang.String queryEmpById(
        @WebParam(name = "queryEmpById", targetNamespace = "")
        java.lang.String queryEmpById
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.Login")
    @WebMethod
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://webservice.employee.xxdai.com/", className = "com.xxdai.external.employee.ws.LoginResponse")
    public java.lang.String login(
        @WebParam(name = "loginParam", targetNamespace = "")
        java.lang.String loginParam
    );
}
