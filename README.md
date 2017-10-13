# idea-test-recorder-plugin
An intelliJ plugin that collects test performance results for later analysis in an influxdb instance.

## How to use
This is currently a work in progress.
- Provide an implementation that just logs test results locally as an alternative to using influxdb
- Add some settings / configuration options in intelliJ to make it more flexible to configure


## How to test
1. Build it on the command line using maven.
2. Install the plugin in intelliJ manually using the 'install from file' option.
   1. A zip file containing the release can be found in the target director of the project
   2. IntelliJ will need to be restarted for it to start working
3. Setup a local influx-grafana docker image e.g. https://hub.docker.com/r/samuelebistoletti/docker-statsd-influxdb-grafana/
4. Run some tests a few times and play around in grafana to see the stats after a few runs
