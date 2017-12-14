package com.jobs.elastic;

import com.bean.ElasticBean;
import com.bean.config.ElasticConfig;
import com.bean.config.ScheduleJob;
import com.test.DataWorkContext;
import com.utils.SpringContextUtil;
import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.cluster.node.stats.NodeStats;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 定时任务运行工厂类
 *
 * @author tq
 * @date 2016/5/1
 */
@DisallowConcurrentExecution
public class ElasticMonitorJob implements Job {

    private static Logger logger = Logger.getLogger(ElasticMonitorJob.class);
    /**
     * 前台配置项
     * Elastic监控配置
     * es.cluster.name=SOC-15
     * es.port=9300
     * nodes=10.2.4.15,10.2.4.42,10.2.4.43
     * capability=false
     */

    private List<String> eshostlist = new ArrayList<String>();

    private List<String> alivelist = new ArrayList<String>();

    private TransportClient client;

    private ElasticClient es;

    private boolean capabilityFlag = true;


    public void execute(JobExecutionContext context) throws JobExecutionException {
        ElasticConfig scheduleJob = (ElasticConfig)context.getMergedJobDataMap().get("scheduleJob");
        logger.info("[ thread id "+ Thread.currentThread().getId()+"]" + "任务名称 = [" + scheduleJob.getJobName() + "]"+ DataWorkContext.count++);
        // 截取初始化属性文件eshost
        logger.info("ElasticMonitor result nodeIps :"+scheduleJob.getNodeIps());
        this.capabilityFlag = scheduleJob.isCapabilityFlag();

        String[] sourceStrArray = scheduleJob.getNodeIps().split(",");
        for (int i = 0; i < sourceStrArray.length; i++) {
            eshostlist.add(sourceStrArray[i]);
        }
        //spring 容器中获取client
        try{
            es = SpringContextUtil.getBean(ElasticClient.class);
        }catch (NoSuchBeanDefinitionException e){
            HashMap attributes = new HashMap();
            attributes.put("nodeIps",scheduleJob.getNodeIps());
            attributes.put("clusterName",scheduleJob.getClusterName());
            attributes.put("nodePort",scheduleJob.getNodePort());
            SpringContextUtil.autoRegisterBean(attributes,"elasticClient",ElasticClient.class);
            es = SpringContextUtil.getBean("elasticClient");
            es.getClient();
            client = es.elasticClient;
            try {
                packageResultBean();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        client = es.elasticClient;
        try {
            packageResultBean();
        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }


    /**
     * 封装ResultBean 并且生成故障事件
     */
    private void packageResultBean() throws Exception{
        //获取集群信息健康度 黄 红 报警  绿色不报警
        ClusterAdminClient clusterAdminClient = client.admin().cluster();
        NodesStatsResponse nsr = clusterAdminClient.prepareNodesStats().all().execute().actionGet();
        NodeStats[] ns = nsr.getNodes();

        //获取存活节点 并且生成数据指标
        for(int i = 0; i<eshostlist.size(); i++){
            String esnodip = eshostlist.get(i);
            for(int j = 0; j<ns.length; j++){
                if(esnodip.equals(ns[j].getHostname())){
                    alivelist.add(ns[j].getHostname());
                    //TODO生成数据 生成性能事件 capabilityFlag为是否开启性能数据
                    if(capabilityFlag){
                        ElasticBean eBean = new ElasticBean();
//                        eBean.setMonitorId(rb.monitorID);
//                        eBean.setEnterDate(rb.time);
                        eBean.setClusterName(nsr.getClusterNameAsString());
                        eBean.setNodeName(ns[j].getHostname());
                        eBean.setHeapUsedPercent(ns[j].getJvm().getMem().getHeapUsedPercent());
                        eBean.setMasterFlag(Boolean.parseBoolean(ns[j].getNode().attributes().get("master")));
                        eBean.setCurrentThread(ns[j].getJvm().getThreads().getCount());
                        eBean.setTotalThread(ns[j].getJvm().getThreads().getPeakCount());
                        eBean.setDocsTotal(ns[j].getIndices().getDocs().getCount());
                        eBean.setOsCupPercent(ns[j].getOs().getCpuPercent());
                        eBean.setOsMen(ns[j].getOs().getMem().getUsedPercent());
                        logger.info("ElasticMonitor ElasticBean:"+eBean.toString());
//                        rb.addInfo(eBean);
                    }
                }
            }
        }

        //报警故障节点事件
        if(eshostlist.removeAll(alivelist)){
            for(int i = 0;i<eshostlist.size();i++){
//                BigDataUtil.packageFaultResultBean(rb, mv,FaultEnum.F_1204058.id,FaultEnum.F_1204058.desc+"  host ip：" + eshostlist.get(i));
            }
        }


        //检测集群健康度 与 性能指标
        checkEsClusterHealthAndCapability(clusterAdminClient);
    }

    /**
     * 检测集群整体健康度   GREEN:正常   YELLOW:部分副本不可用   RED:部分分片不可用
     * 检测集群性能指标项目
     */
    private void checkEsClusterHealthAndCapability(ClusterAdminClient clusterAdminClient){
        ClusterStatsResponse esStatus = clusterAdminClient.prepareClusterStats().execute().actionGet();
        String clusterStatus = String.valueOf(esStatus.getStatus());
        if("YELLOW".equals(clusterStatus)){
//            BigDataUtil.packageFaultResultBean(rb, mv,FaultEnum.F_1204059.id,FaultEnum.F_1204059.desc+" 部分副本不可用 黄色警告");
        }else if("RED".equals(clusterStatus)){
//            BigDataUtil.packageFaultResultBean(rb, mv,FaultEnum.F_1204059.id,FaultEnum.F_1204059.desc+" 部分分片不可用");
        }
    }

}