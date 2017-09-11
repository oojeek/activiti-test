package com.sealinetech.activiti;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class Program {
    public static void main(String[] args) throws SQLException {
        System.out.println("1111");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("2222");
        RuntimeService runtimeService = applicationContext.getBean("runtimeService", RuntimeService.class);
        IdentityService identityService = applicationContext.getBean("identityService", IdentityService.class);
        RepositoryService repositoryService = applicationContext.getBean("repositoryService", RepositoryService.class);
        TaskService taskService = applicationContext.getBean("taskService", TaskService.class);

        System.out.println(runtimeService.toString());
        System.out.println("************************************************** user");
        List<User> userList = identityService.createUserQuery().list();
        userList.forEach(user -> System.out.println(user.getId()));
        System.out.println("**************************************************");
        List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery().list();
        definitionList.forEach(info -> System.out.println(info.getKey()));
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("saleProcess", "1");
        //System.out.println(instance);
        System.out.println("************************************************** instanceList");
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().involvedUser("kermit").list();
        instanceList.forEach(info -> System.out.println(info.getId() + "--" + info.getName() + "--" + info.getBusinessKey() + "--" + info.getTenantId()));
        System.out.println("**************************************************");

        List<Task> taskList = taskService.createTaskQuery().list();
        taskList.forEach(info -> System.out.println(info.getName()));

        Task task1 = taskList.get(0);
        taskService.complete(task1.getId());
    }
}

