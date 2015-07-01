package hudson.plugins.scm_sync_configuration.strategies.impl;

import hudson.XmlFile;
import hudson.model.Item;
import hudson.model.Saveable;
import hudson.plugins.scm_sync_configuration.model.MessageWeight;
import hudson.plugins.scm_sync_configuration.model.WeightedMessage;
import hudson.plugins.scm_sync_configuration.strategies.AbstractScmSyncStrategy;
import hudson.plugins.scm_sync_configuration.strategies.model.JobOrFolderConfigurationEntityMatcher;
import hudson.plugins.scm_sync_configuration.strategies.model.PageMatcher;

import java.util.Collections;

public class JobConfigScmSyncStrategy extends AbstractScmSyncStrategy {

    public JobConfigScmSyncStrategy(){
		super(new JobOrFolderConfigurationEntityMatcher(), Collections.singletonList(new PageMatcher("^(.*view/[^/]+/)?(job/[^/]+/)+configure$", "form[name='config']")));
	}

    public CommitMessageFactory getCommitMessageFactory(){
        return new CommitMessageFactory(){
            public WeightedMessage getMessageWhenSaveableUpdated(Saveable s, XmlFile file) {
                return new WeightedMessage("Job ["+((Item)s).getName()+"] configuration updated",
                        // Job config update message should be considered as "important", especially
                        // more important than the plugin descriptors Saveable updates
                        MessageWeight.IMPORTANT);
            }
            public WeightedMessage getMessageWhenItemRenamed(Item item, String oldPath, String newPath) {
                return new WeightedMessage("Job ["+item.getName()+"] hierarchy renamed from ["+oldPath+"] to ["+newPath+"]",
                        // Job config rename message should be considered as "important", especially
                        // more important than the plugin descriptors Saveable renames
                        MessageWeight.MORE_IMPORTANT);
            }
            public WeightedMessage getMessageWhenItemDeleted(Item item) {
                return new WeightedMessage("Job ["+item.getName()+"] hierarchy deleted",
                        // Job config deletion message should be considered as "important", especially
                        // more important than the plugin descriptors Saveable deletions
                        MessageWeight.MORE_IMPORTANT);
            }
        };
    }
}
