package com.ruoyi.leave.instener;

import com.ruoyi.leave.domain.WorkflowLeave;
import com.ruoyi.leave.service.IWorkflowLeaveService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;

public class LeaveEndStateListener implements ExecutionListener {
    private Expression state;
    @Autowired
    private IWorkflowLeaveService workflowLeaveService;
    @Override
    public void notify(DelegateExecution delegateExecution) {
        WorkflowLeave workflowLeave = new WorkflowLeave();
        workflowLeave.setId(delegateExecution.getProcessInstanceBusinessKey());
        workflowLeave.setState(state.getValue(delegateExecution).toString());
       workflowLeaveService.updateWorkflowLeave(workflowLeave);


    }
}
