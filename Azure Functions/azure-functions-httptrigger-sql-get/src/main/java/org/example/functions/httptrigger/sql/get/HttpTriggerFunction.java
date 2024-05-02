package org.example.functions.httptrigger.sql.get;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.example.functions.httptrigger.sql.get.constants.ConfigConstants;
import org.example.functions.httptrigger.sql.get.model.Employee;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerFunction {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     *
     */
    @FunctionName("mysqldbtrigger")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        String sqlConnectionString = System.getenv(ConfigConstants.sqlconnectionstring);

        ResultSet resultSet=null;

        Connection connection=null;

        List<Employee> employeeList=new ArrayList<>();

        try{
            connection= DriverManager.getConnection(sqlConnectionString);
            Statement statement=connection.createStatement();
            String selectSql="select * from [dbo].[employee]";
            resultSet=statement.executeQuery(selectSql);
            while (resultSet.next())
            {
                employeeList.add(new Employee(resultSet.getString(1),resultSet.getString(2),resultSet.getLong(3)));
            }

        }
        catch (Exception e)
        {
            context.getLogger().info("exception raised waste fellow take care.: {}"+e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        context.getLogger().info("Java HTTP trigger processed a request.");


            return request.createResponseBuilder(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(employeeList)
                                    .build();
    }


    @FunctionName("mysqldbtriggerforselect")
    public HttpResponseMessage insertSql(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Employee> request,
            final ExecutionContext context) {

        String sqlConnectionString = System.getenv(ConfigConstants.sqlconnectionstring);

        ResultSet resultSet=null;

        Connection connection=null;

        Employee employee=request.getBody();


        context.getLogger().info("Java HTTP trigger processed a request.");


        String resultMessage=null;

        try{
            connection= DriverManager.getConnection(sqlConnectionString);
            String insertSql="insert into [dbo].[employee](eid,name,salary) values (?,?,?);";
            PreparedStatement statement=connection.prepareStatement(insertSql);
            statement.setString(1,employee.getEid());
            statement.setString(2,employee.getName());
            statement.setLong(3,employee.getSalary());
            statement.executeUpdate();
            resultMessage="inserted successfully";
        }
        catch (Exception e)
        {
            context.getLogger().info("exception raised waste fellow take care.: {}"+e);
            resultMessage="sorry some thing goes rong";
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return request.createResponseBuilder(HttpStatus.OK).body("inserted successfully").build();
    }
}



//  "Spring.datasource.url":"jdbc:sqlserver://pain.database.windows.net:1433;database=jpa;user=madhura@pain;password=gysjdwfB@799iS;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30",
//          "Spring.jpa.show-sql": true,
//          "Spring.jpa.generate-ddl":true,
//          "Spring.jpa.database-platform":"org.hibernate.dialect.H2Dialect",
//          "Spring.jpa.properties.hibernate.format_sql": true,
//          "Spring.jpa.properties.hibernate.ddl-auto":"update",
//          "Spring.jpa.properties.hibernate.dialect":"org.hibernate.dialect.SQLServerDialect",
//          "Spring.jpa.properties.hibernate.naming.physical-strategy":"org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl",
//          "Spring.jpa.properties.hibernate.jdbc.batch_size":"4",
//          "Spring.jpa.properties.hibernate.jdbc.order_inserts":true,
//          "Spring.mvc.pathmatch.matching-strategy":"ant_path_matcher"