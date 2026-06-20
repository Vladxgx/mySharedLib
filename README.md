# mySharedLib

This is my Jenkins shared library for the final project.

The app pipeline in `HELM-FLASK-EXAM` uses this library so the Jenkinsfile stays
cleaner and the repeated steps are stored in one place.

Current library files:

- `vars/sourceControl.groovy` - simple Git status/clone/push helpers.
- `vars/dockering.groovy` - Docker build, push, pull, run, and stop helpers.
- `vars/tests.groovy` - Python compile check, Bandit scan, Trivy base image scan, and Trivy app image scan.

In Jenkins this library is configured as `mySharedLib`, then used in the app
Jenkinsfile with:

```groovy
@Library('mySharedLib') _
```

This repo does not deploy anything by itself. It only provides helper functions
for the Jenkins pipeline.
