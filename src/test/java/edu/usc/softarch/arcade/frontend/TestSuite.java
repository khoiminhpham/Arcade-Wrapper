package edu.usc.softarch.arcade.frontend;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import edu.usc.softarch.arcade.frontend.features.pipeextractor.*;
import edu.usc.softarch.arcade.frontend.features.arc.*;
import edu.usc.softarch.arcade.frontend.exttooladapters.mallet.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  PipeExtractorWrapperTst.class,
  TopicModelGeneratorTst.class,
  InferencerTst.class,
  ArcWrapperTst.class
})
public class TestSuite { }
