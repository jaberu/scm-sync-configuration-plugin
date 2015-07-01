package hudson.plugins.scm_sync_configuration;

import hudson.model.Hudson;

import java.io.File;

public class JenkinsFilesHelper {

	public static String buildPathRelativeToHudsonRoot(File file){
		File hudsonRoot = Hudson.getInstance().getRootDir();
		if(!file.getAbsolutePath().startsWith(hudsonRoot.getAbsolutePath())){
			return null;
		}
		String truncatedPath = file.getAbsolutePath().substring(hudsonRoot.getAbsolutePath().length()+1); // "+1" because we don't need ending file separator
		return truncatedPath.replaceAll("\\\\", "/"); 
	}

    public static File buildFileFromPathRelativeToHudsonRoot(String pathRelativeToHudsonRoot){
        File hudsonRoot = Hudson.getInstance().getRootDir();
        return new File(hudsonRoot.getAbsolutePath()+File.separator+pathRelativeToHudsonRoot);
    }
}
