# idea-test-recorder-plugin
An intelliJ plugin that collects test performance results for later analysis

## How to use
This is currently a work in progress.
- Build a proper server that can be used to persist and report on data
- Provide an implementation that just logs test results locally
- Add some settings / configuration options in intelliJ to make it more flexible to configure

## How to test
1. Build it on the command line using maven. 
2. Install the plugin in intelliJ manually using the 'install from file' option. 
   1. A zip file containing the release can be found in the target director of the project
   2. IntelliJ will need to be restarted for it to start working
3. Run the TestHost class in intellij
4. Run a Test in a project, the test results should be picked up by the TestHost.

