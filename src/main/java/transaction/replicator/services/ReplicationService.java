package transaction.replicator.services;

import org.springframework.beans.factory.annotation.Autowired;
import transaction.replicator.config.YAMLConfig;

public class ReplicationService {

    @Autowired
    private YAMLConfig myConfig;

    public String replicate() {
        return myConfig.getTarget().get(0).getTargetConnectionUrl();
    }
}
