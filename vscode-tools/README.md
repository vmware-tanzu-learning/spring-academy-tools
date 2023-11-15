# README

This is the *Visual Studio Code Tools* project.

The resources and scripts here,
along with the `.github/workflows/vscode-tools.yml` workflow are used
to build an Educates overlay package that can be sourced in
workshops that will using the VS Code Java extension package,
as well as default settings intended to streamline the user
experience.

## Usage

Using the Visual Studio Code Tools overlay is simply configuring
a vendir specification in an Educates workshop definition as follows:

```yaml
apiVersion: training.educates.dev/v1beta1
kind: Workshop
metadata:
  name: ...
spec:
  title: ...
  ...
  workshop:
    image: jdk17-environment:*
    ...
    files:
      ...
      - path: .local/share/code-server
        image:
          url: ghcr.io/vmware-tanzu-learning/vscode-java-tools-$(platform_arch)-files:0.25.14
      ...
```

Note that the `image.url` points to the overlay package oci image in github container registry.
The version `0.25.14` happens to align with the version of the VS Code Java Extension Package
in use with Code Server running in Educates `2.6.x`.

Note the `$(platform_arch)` substitution parameter will passed during Educates workshop session
initialization,
calculating to the container platform where the hosting Educates provider is running.
For remote environments, the platform will be `amd64`.
For local environments on MacOSX M-series, the platform will resolve to `arm64`.

## Resources

Default VS code settings are configured in the `resources/settings.json` file.

The following are additions to the defaults provided by code-server running on Educates:

- `"java.compile.nullAnalysis.mode": "disabled",`: will suppress the interactive popup for null analysis.
- `"java.configuration.updateBuildConfiguration": "automatic"`: will suppress interactive prompt for interactive builds.

## Scripts

The `scripts/build-tools-package.sh` script is used to build platform specific versions of
the VS Code tools.

It is used by the Github workflow to build on `amd64` platform,
and can also be run on M1 Mac platforms to build/publish versions for `arm64`

## Workflow

The `vscode-tools.yml` workflow is used to psuedo-automate building of vs code tools overlay package
for `amd64` platform.
While the options have an option to choose alternate platform, it does not yet support building
for `arm64` platform.
