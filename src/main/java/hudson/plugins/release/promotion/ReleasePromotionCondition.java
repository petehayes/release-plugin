/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hudson.plugins.release.promotion;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Descriptor.FormException;
import hudson.plugins.promoted_builds.PromotionBadge;
import hudson.plugins.promoted_builds.PromotionCondition;
import hudson.plugins.promoted_builds.PromotionConditionDescriptor;
import hudson.plugins.release.ReleaseWrapper.ReleaseBuildBadgeAction;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Add support for the promotion plugin by adding a condition that the build
 * must be a released build.
 *
 * @author Peter Hayes
 */
public class ReleasePromotionCondition extends PromotionCondition {

    @Override
    public PromotionBadge isMet(AbstractBuild<?, ?> build) {
        if (build.getAction(ReleaseBuildBadgeAction.class) != null)
            return new Badge();
        
        return null;
    }

    public static final class Badge extends PromotionBadge {
        
    }

    @Extension
    public static final class DescriptorImpl extends PromotionConditionDescriptor {
        public boolean isApplicable(AbstractProject<?,?> item) {
            return true;
        }

        public String getDisplayName() {
            return "If the build is a release build";
        }

        public String getHelpFile() {
            return "/plugin/release/promotion/release.html";
        }

        public PromotionCondition newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return new ReleasePromotionCondition();
        }

        public static final DescriptorImpl INSTANCE = new DescriptorImpl();
    }
}
