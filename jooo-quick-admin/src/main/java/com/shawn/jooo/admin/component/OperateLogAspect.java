package com.shawn.jooo.admin.component;

import com.shawn.jooo.admin.rbac.entity.SysOperateLog;
import com.shawn.jooo.admin.rbac.service.SysOperateLogService;
import com.shawn.jooo.admin.utils.UserUtils;
import com.shawn.jooo.framework.log.annotation.Log;
import com.shawn.jooo.framework.utils.JSONUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Aspect
@Component
public class OperateLogAspect {

    private static List<String> opreateNameList = new ArrayList<>();

    static {
        opreateNameList.add("edit");
        opreateNameList.add("update");
        opreateNameList.add("insert");
        opreateNameList.add("save");
        opreateNameList.add("add");
        opreateNameList.add("delete");
        opreateNameList.add("remove");
        opreateNameList.add("enable");
        opreateNameList.add("disable");
    }

    @Autowired
    private SysOperateLogService sysOperateLogService;

    @Pointcut("@annotation(com.shawn.jooo.framework.log.annotation.Log) " +
            "|| @annotation(org.springframework.web.bind.annotation.RestController)" +
            "|| @annotation(org.springframework.stereotype.Controller)")
    public void OperateLogPointCut() {
    }

     @Pointcut("execution(* com.shawn.jooo..*.contriller..*.*(..))")
    public void OperateLogExceptionPointCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "OperateLogPointCut()", returning = "keys")
    public void saveOperateLog(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        SysOperateLog operateLog = new SysOperateLog();
        try {

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            Log log = method.getAnnotation(Log.class);
            if (log != null) {

            }

            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            operateLog.setMethodName(methodName); // 请求方法

            // 请求的参数
            Map<String, String[]> rtnMap = request.getParameterMap();
            // 将参数所在的数组转换成json
            String params = JSONUtils.toJSONString(rtnMap);

            operateLog.setRequestParam(params); // 请求参数
            operateLog.setJsonResult(JSONUtils.toJSONString(keys)); // 返回结果
            operateLog.setOperateUser(UserUtils.getLoginUsername()); // 请求用户
            //operateLog.setOperateIp(IPUtils.getRemortIP(request)); // 请求IP
            operateLog.setOperatePath(request.getRequestURI()); // 请求URI
            operateLog.setOperateTime(System.currentTimeMillis()); // 创建时间
            sysOperateLogService.insert(operateLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "OperateLogExceptionPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        SysOperateLog operateLog = new SysOperateLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();

        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }
}
