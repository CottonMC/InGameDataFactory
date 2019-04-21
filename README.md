<img src="icon.png" align="right" width="180px"/>

# In-Game Data Factory

[>> Downloads <<](https://github.com/CottonMC/ingame-data-factory/releases)

*Generate data files in-game!*

**This mod is open source and under a permissive license.** As such, it can be included in any modpack on any platform without prior permission. We appreciate hearing about people using our mods, but you do not need to ask to use them. See the [LICENSE file](LICENSE) for more details.

## Usage

Adds a single client-side command, `/igdf`. You can list all subcommands
with `/igdf help`.

### Generating

You can use `/igdf generate <id> <generator>` to generate a single file,
or `/igdf generateset <id> <set>` to generate multiple files. See the
autocompletion for all supported generators.

IGDF generates files in the resource pack directory. You can change the
pack name with `/igdf changeoutputpath` and generate a pack.mcmeta for it
with `/igdf packmcmeta`.

Note: `<id>` must be an ID with the format `modname:my_thing`.

Uses [JSON Factory](https://github.com/CottonMC/json-factory) as the
generation backend.
