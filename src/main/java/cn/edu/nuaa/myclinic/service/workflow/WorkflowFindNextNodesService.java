package cn.edu.nuaa.myclinic.service.workflow;

import camundafeel.de.odysseus.el.ExpressionFactoryImpl;
import camundafeel.de.odysseus.el.util.SimpleContext;
import camundafeel.javax.el.ExpressionFactory;
import camundafeel.javax.el.ValueExpression;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WorkflowFindNextNodesService {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowFindNextNodesService.class);
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 获取下一个节点任务信息
     *
     * @param processInstanceId
     *            流程实例ID
     * @return 下一个节点信息
     * @throws Exception
     */
    public List<TaskDefinition> getNextTaskInfos(String processInstanceId, Map<String, Object> condition) throws Exception {
        try {
            ProcessDefinitionEntity processDefinitionEntity = null;
            String id = null;
            // List<TaskDefinition> tasks =
            // null;//会出现java.util.ConcurrentModificationException异常，改成CopyOnWriteArrayList
            List<TaskDefinition> tasks = new CopyOnWriteArrayList<TaskDefinition>();
            // 获取流程发布Id信息
            String definitionId = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
                    .singleResult().getProcessDefinitionId();
            processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                    .getDeployedProcessDefinition(definitionId);
            ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery()
                    .executionId(processInstanceId).singleResult();
            // 当前流程节点Id信息
            String activitiId = execution.getActivityId();
            logger.info("execution----->" + activitiId);
            // 获取流程所有节点信息
            List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();

            JSONArray ja = new JSONArray();
            // 遍历所有节点信息
            for (ActivityImpl activityImpl : activitiList) {
                id = activityImpl.getId();
                logger.info("id----------->" + id + "----->" + activityImpl.getProperty("type"));
                if (activitiId.equals(id)) {
                    // 获取下一个节点信息
                    tasks = nextTaskDefinitions(activityImpl, activityImpl.getId(), processInstanceId, condition);
                    break;
                }
            }

            return tasks;
        } catch (Exception e) {

            logger.error("operation error", e);
            throw e;
        }
    }

    private List<TaskDefinition> nextTaskDefinitions(ActivityImpl activityImpl, String activityId,
                                                     String processInstanceId, Map<String, Object> condition) {
        try {
            PvmActivity ac = null;
            Object s = null;
            logger.info("activityImpl.getActivityId()---->"+activityImpl.getActivityId()+"---activityId---------->"+activityId+"--->"+activityImpl.getProperty("type"));
            // List<TaskDefinition> taskDefinitions = new
            // ArrayList<TaskDefinition>();
            // //会出现java.util.ConcurrentModificationException异常，改成CopyOnWriteArrayList
            List<TaskDefinition> taskDefinitions = new CopyOnWriteArrayList<TaskDefinition>();
            // 如果遍历节点为用户任务并且节点不是当前节点信息
            if ("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())) {
                // 获取该节点下一个节点信息
                TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl.getActivityBehavior())
                        .getTaskDefinition();
                taskDefinitions.add(taskDefinition);
            }else if (activityImpl.getProperty("type").toString().contains("EndEvent") && !activityId.equals(activityImpl.getId())) {
                // 设置结束节点
                TaskDefinition taskDefinition = new TaskDefinition(null);
                ExpressionManager expressionManager = new ExpressionManager();
                taskDefinition.setKey(activityImpl.getId() == null ? "end" : activityImpl.getId());
                String name = activityImpl.getProperty("name") == null ? "结束" : activityImpl.getProperty("name").toString();
                taskDefinition.setNameExpression(expressionManager.createExpression(name));
                taskDefinitions.add(taskDefinition);
            } else if ("multiInstanceBody".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())) {
                // 获取该节点下一个节点信息
                List<ActivityImpl> list = ((ActivityImpl) activityImpl).getActivities();
                for(ActivityImpl act : list){
                    //logger.info("act-------------->"+act.getActivityBehavior().getClass().getTypeName());
                    TaskDefinition taskDefinition = ((UserTaskActivityBehavior) act.getActivityBehavior())
                            .getTaskDefinition();
                    taskDefinitions.add(taskDefinition);
                }
            } else if ("exclusiveGateway".equals(activityImpl.getProperty("type"))
                    || "inclusiveGateway".equals(activityImpl.getProperty("type"))) {// 当前节点为exclusiveGateway或inclusiveGateway
                List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
                String defaultTransition = (String) activityImpl.getProperty("default");
                if (outTransitions.size() == 1) {
                    taskDefinitions.addAll(nextTaskDefinitions((ActivityImpl) outTransitions.get(0).getDestination(),
                            activityId, processInstanceId, condition));
                } else if (outTransitions.size() > 1) { // 如果排他网关有多条线路信息
                    for (PvmTransition tr1 : outTransitions) {
                        ActivityImpl actImpl = (ActivityImpl) tr1.getDestination();
                        if (actImpl.getProperty("type").toString().contains("EndEvent")) {
                            TaskDefinition taskDefinition = new TaskDefinition(null);
                            ExpressionManager expressionManager = new ExpressionManager();
                            taskDefinition.setKey(actImpl.getId() == null ? "end" : actImpl.getId());
                            String name = actImpl.getProperty("name") == null ? "结束"
                                    : actImpl.getProperty("name").toString();
                            taskDefinition.setNameExpression(expressionManager.createExpression(name));
                            taskDefinitions.add(taskDefinition);
                            break;
                        }
                        s = tr1.getProperty("conditionText"); // 获取排他网关线路判断条件信息
                        if (null == s) {
                            continue;
                        }
                        // 判断el表达式是否成立
                        if (isCondition(condition, StringUtils.trim(s.toString()))) {
                            taskDefinitions.addAll(nextTaskDefinitions((ActivityImpl) tr1.getDestination(), activityId,
                                    processInstanceId, condition));
                        }
                    }
                    if(taskDefinitions.size()==0 && StringUtils.isNotBlank(defaultTransition)){
                        for (PvmTransition tr3 : outTransitions) {
                            if(defaultTransition.equals(tr3.getId())){
                                ActivityImpl actImpl = (ActivityImpl) tr3.getDestination();
                                if (actImpl.getProperty("type").toString().contains("EndEvent")) {
                                    TaskDefinition taskDefinition2 = new TaskDefinition(null);
                                    ExpressionManager expressionManager2 = new ExpressionManager();
                                    taskDefinition2.setKey(actImpl.getId() == null ? "end" : actImpl.getId());
                                    String name2 = actImpl.getProperty("name") == null ? "结束"
                                            : actImpl.getProperty("name").toString();
                                    taskDefinition2.setNameExpression(expressionManager2.createExpression(name2));
                                    taskDefinitions.add(taskDefinition2);
                                    break;
                                }

                                taskDefinitions.addAll(nextTaskDefinitions( actImpl,
                                        activityId, processInstanceId, condition));
                                logger.info("taskDefinitions---333333333--->"+taskDefinitions.size());
                            }
                        }
                    }
                }
            } else if ("parrallelGateway".equals(activityImpl.getProperty("type"))) {
                List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
                for (PvmTransition tr1 : outTransitions) {
                    taskDefinitions.addAll(nextTaskDefinitions((ActivityImpl) tr1.getDestination(), activityId,
                            processInstanceId, condition));
                }
            } else {
                // 获取节点所有流向线路信息
                List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
                List<PvmTransition> outTransitionsTemp = null;
                for (PvmTransition tr : outTransitions) {
                    ac = tr.getDestination(); // 获取线路的终点节点
                    logger.info("ac----------->" + ac.getId() + "------>" + ac.getProperty("type"));
                    // 如果流向线路为排他网关或包容网关
                    if ("exclusiveGateway".equals(ac.getProperty("type"))
                            || "inclusiveGateway".equals(ac.getProperty("type"))) {
                        outTransitionsTemp = ac.getOutgoingTransitions();
                        String defaultTransition = (String) ac.getProperty("default");
                        // 如果排他网关只有一条线路信息
                        if (outTransitionsTemp.size() == 1) {
                            taskDefinitions.addAll(
                                    nextTaskDefinitions((ActivityImpl) outTransitionsTemp.get(0).getDestination(),
                                            activityId, processInstanceId, condition));
                        } else if (outTransitionsTemp.size() > 1) { // 如果排他网关有多条线路信息
                            for (PvmTransition tr1 : outTransitionsTemp) {
                                ActivityImpl actImpl = (ActivityImpl) tr1.getDestination();
                                if (actImpl.getProperty("type").toString().contains("EndEvent")) {
                                    TaskDefinition taskDefinition2 = new TaskDefinition(null);
                                    ExpressionManager expressionManager2 = new ExpressionManager();
                                    taskDefinition2.setKey(actImpl.getId() == null ? "end" : actImpl.getId());
                                    String name2 = actImpl.getProperty("name") == null ? "结束"
                                            : actImpl.getProperty("name").toString();
                                    taskDefinition2.setNameExpression(expressionManager2.createExpression(name2));
                                    taskDefinitions.add(taskDefinition2);
                                    break;
                                }

                                logger.info("taskDefinitions--1111---->"+taskDefinitions.size());
                                s = tr1.getProperty("conditionText"); // 获取排他网关线路判断条件信息
                                if (null == s) {
                                    continue;
                                }
                                // 判断el表达式是否成立
                                if (isCondition(condition, StringUtils.trim(s.toString()))) {
                                    taskDefinitions.addAll(nextTaskDefinitions(actImpl,activityId, processInstanceId, condition));
                                }

                                logger.info("taskDefinitions---22222--->"+taskDefinitions.size());
                            }
                            if(taskDefinitions.size()==0 && StringUtils.isNotBlank(defaultTransition)){
                                for (PvmTransition tr3 : outTransitionsTemp) {
                                    if(defaultTransition.equals(tr3.getId())){
                                        ActivityImpl actImpl = (ActivityImpl) tr3.getDestination();
                                        if (actImpl.getProperty("type").toString().contains("EndEvent")) {
                                            TaskDefinition taskDefinition2 = new TaskDefinition(null);
                                            ExpressionManager expressionManager2 = new ExpressionManager();
                                            taskDefinition2.setKey(actImpl.getId() == null ? "end" : actImpl.getId());
                                            String name2 = actImpl.getProperty("name") == null ? "结束"
                                                    : actImpl.getProperty("name").toString();
                                            taskDefinition2.setNameExpression(expressionManager2.createExpression(name2));
                                            taskDefinitions.add(taskDefinition2);
                                            break;
                                        }

                                        taskDefinitions.addAll(nextTaskDefinitions( actImpl,
                                                activityId, processInstanceId, condition));
                                        logger.info("taskDefinitions---333333333--->"+taskDefinitions.size());
                                    }
                                }
                            }
                        }
                    } else if ("userTask".equals(ac.getProperty("type"))) {
                        taskDefinitions.add(((UserTaskActivityBehavior) ((ActivityImpl) ac).getActivityBehavior())
                                .getTaskDefinition());
                    } else if ("multiInstanceBody".equals(ac.getProperty("type"))) {
                        List<ActivityImpl> list = ((ActivityImpl) ac).getActivities();
                        for(ActivityImpl act : list){
                            //logger.info("act-------------->"+act.getActivityBehavior().getClass().getTypeName());
                            TaskDefinition taskDefinition = ((UserTaskActivityBehavior) act.getActivityBehavior())
                                    .getTaskDefinition();
                            taskDefinitions.add(taskDefinition);
                        }
                    } else if (ac.getProperty("type").toString().contains("EndEvent")) {
                        // 设置结束节点
                        TaskDefinition taskDefinition = new TaskDefinition(null);
                        ExpressionManager expressionManager = new ExpressionManager();
                        taskDefinition.setKey(ac.getId() == null ? "end" : ac.getId());
                        String name = ac.getProperty("name") == null ? "结束" : ac.getProperty("name").toString();
                        taskDefinition.setNameExpression(expressionManager.createExpression(name));
                        taskDefinitions.add(taskDefinition);
                    } else if ("parrallelGateway".equals(ac.getProperty("type"))) {
                        List<PvmTransition> poutTransitions = ac.getOutgoingTransitions();
                        for (PvmTransition tr1 : poutTransitions) {
                            taskDefinitions.addAll(nextTaskDefinitions((ActivityImpl) tr1.getDestination(), activityId,
                                    processInstanceId, condition));
                        }
                    }
                }
            }
            return taskDefinitions;

        } catch (Exception e) {
            logger.error("operation error", e);
            throw e;
        }
    }

    private boolean isCondition(Map<String, Object> condition, String el) {
        try {
            ExpressionFactory factory = new ExpressionFactoryImpl();
            SimpleContext context = new SimpleContext();
            if (condition != null) {
                Iterator<Map.Entry<String, Object>> iterator = condition.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> value = iterator.next();
                    context.setVariable(value.getKey(), factory.createValueExpression(value.getValue(), String.class));
                }
            }
            ValueExpression e = factory.createValueExpression(context, el, boolean.class);
            return (Boolean) e.getValue(context);
        } catch (Exception e) {
            logger.error("operation error", e);
            throw e;
        }
    }
}
