/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.hudson;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

/**
 * EmotionalIkeRecorder
 * 
 * Based on http://wiki.hudson-ci.org/display/HUDSON/Emotional+Hudson+Plugin
 *
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class EmotionalIkeRecorder extends Recorder
{
   public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
         throws InterruptedException, IOException
   {
      build.getActions().add(new EmotionalIkeAction(build.getResult()));
      return true;
   }

   public BuildStepMonitor getRequiredMonitorService()
   {
      return BuildStepMonitor.BUILD;
   }

   @Extension
   public static class EmotionalArquillianDescriptor extends BuildStepDescriptor<Publisher>
   {
      public EmotionalArquillianDescriptor()
      {
         super(EmotionalIkeRecorder.class);
         load();
      }

      @Override
      public String getDisplayName()
      {
         return "Emotional Ike";
      }

      @Override
      public boolean configure(StaplerRequest req, JSONObject json) throws hudson.model.Descriptor.FormException
      {
         save();
         return super.configure(req, json);
      }

      @Override
      public Publisher newInstance(StaplerRequest req, JSONObject formData) throws FormException
      {
         return new EmotionalIkeRecorder();
      }

      @SuppressWarnings("rawtypes")
      @Override
      public boolean isApplicable(Class<? extends AbstractProject> jobType)
      {
         return true;
      }
   }
}
