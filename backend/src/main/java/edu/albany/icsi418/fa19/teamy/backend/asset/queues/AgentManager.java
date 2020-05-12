package edu.albany.icsi418.fa19.teamy.backend.asset.queues;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Manages application startup/shutdown related QueueAgent events. Will spin up 1 QueueAgent
 * at startup and shut it down at shutdown. Also holds the current queue list.
 */
@Component
public class AgentManager {

    private static final Logger log = LoggerFactory.getLogger(AgentManager.class);

    protected static final PriorityBlockingQueue<APIQueueItem> assetQueue = new PriorityBlockingQueue<>();

    private List<QueueAgent> activeAgents = new ArrayList<>();

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            QueueAgent queueAgent = (QueueAgent) event.getApplicationContext().getBean("queueAgent");
            log.info("Starting Queue Agent on App Startup");
            queueAgent.start();
            activeAgents.add(queueAgent);
            log.info("Enqueueing New Assets");
            AgentStartup startupAgent = (AgentStartup) event.getApplicationContext().getBean("agentStartup");
            startupAgent.start();
        } catch (BeansException ex) {
            log.error("Could not get QueueAgent bean at startup", ex);
        }
    }

    @EventListener
    public void onApplicationEvent(ContextStoppedEvent event) {
        activeAgents.forEach(Thread::interrupt);
        activeAgents.clear();
    }
}
