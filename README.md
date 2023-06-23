# cnc-devcontainer-templates

### A quick note for non-VSCode users.

After 20+ years of emacs, I switched to VSCode for various reasons.  While these setups are tailor-made for VSCode, both
Devcontainers and Github Codespaces have both remote and local solutions for other editors. I encourage you to search
around for solutions to make it work for your preferred setup.

## Templates
| Codespace Type | Link |
| ---- | ---- |
| Single Image (default): | [![Open in Github Codespaces Single Image](https://github.com/codespaces/badge.svg)](https://codespaces.new/jwhitlark/cnc-devcontainer-templates) |
| Docker Compose: | [![Open in GitHub Codespaces Docker Compose](https://github.com/codespaces/badge.svg)](https://codespaces.new/jwhitlark/cnc-devcontainer-templates?devcontainer_path=.devcontainer%2Fbasic-compose%2Fdevcontainer.json) |
| Kubernetes (k3s via k3d) | [![Open in GitHub Codespaces Kubernetes (k3s via k3d)](https://github.com/codespaces/badge.svg)](https://codespaces.new/jwhitlark/cnc-devcontainer-templates?devcontainer_path=.devcontainer%2Fbasic-k3s%2Fdevcontainer.json) |

## When would you use these?

### Single Image
Quick experiments, Simple tools, or projects with Sass only dependencies.

### Docker Compose
Simple setups, or projects with multiple local dependencies that are not intended to deployed to Kubernetes in production.

### Kubernetes
You're going full Cloud Native Clojure, and want to dev as you prod!

## Common to all templates

All include

* clojure 1.11  (1.12 is going to be awesome!)
* tools-deps
* babashka
* lein
* clj-new
* deps-new

### VSCode liveshare

All these examples are fully compatible with [VSCode liveshare](https://code.visualstudio.com/learn/collaboration/live-share), which is one of the killer features of this platform.  Being able to work together on on code without setting up an additional environment while using your preferred editor settings is a delight.

### Finding, getting info, and installing additional packages

    apt update
    apt search XYZ
    apt show XYZ
    apt install XYZ

### Accessing Private JARs

See [Managing Access to other Repositories](https://docs.github.com/en/codespaces/managing-your-codespaces/managing-repository-access-for-your-codespaces)

### Github actions, issues, etc.

### Minimal VSCode setup

If you don't use vscode, I recommend setting up a minimal profile that emulates your favorite editor, and turning on settings sync.  That way you will always have a minimally functional editor when you use a codespace.

Note that you can configure your preference between the web editor, local VSCode, and other editors.

You will need to specifically allow your repositories to access your shared configuration.

Configure both of these and more at [Your codespace settings](https://github.com/settings/codespaces)

### Dotfiles

You can specify a dotfile repository, that will be injected into your devcontainer.

[Docs](https://docs.github.com/en/codespaces/customizing-your-codespace/personalizing-github-codespaces-for-your-account#dotfiles)

Configure at [Your codespace settings](https://github.com/settings/codespaces)

### Prebuilds

Github actions can be automatically setup to pre-build your devcontainer docker images, dramatically speeding up startup.

https://docs.github.com/en/codespaces/prebuilding-your-codespaces/configuring-prebuilds

### Differences between devcontainers on your local machine and Github Codespaces


### Setting a minimum machine specification

[codespaces - minimum machine settings](https://docs.github.com/en/enterprise-cloud@latest/codespaces/setting-up-your-project-for-codespaces/configuring-dev-containers/setting-a-minimum-specification-for-codespace-machines)

## Single container specifics
## Docker compose specifics
## Kubernetes specifics
### K3d commands

    k3d cluster create

    k3d cluster create --registry-create myregistry  > k3d.log 2>&1

    k3d cluster list

    k3d cluster start

Add the following to auto deploy manifests?

    --volume /tmp/test-manifests:/var/lib/rancher/k3s/server/manifests/test