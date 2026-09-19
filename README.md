# Mine Origins (Minecraft 26.2 - Fabric)

**Mine Origins** is a rich, modern exploration and metallurgy mod for Minecraft 26.2 running on Fabric. It introduces realistic ore processing suites (Aluminium and Titanium), a tropical coconut palm ecosystem with functional ropes, wildlife such as high-altitude hunting Eagles, and a floating celestial dimension — the **Aetherial Aurora**.

---

## 🌟 Mod Features Overview

- **Metallurgy & Ores**:
  - **Bauxite & Aluminium Suite**: Natural Bauxite deposits, raw bauxite chunks, smelted aluminium ingots, and storage blocks.
  - **Ilmenite, Rutile & Titanium Suite**: Deepslate Ilmenite and high-tier Rutile ores, raw titanium, titanium ingots, and reinforced blocks.
  - **Sky Metal & Chroma Ore**: Extraterrestrial materials discovered on floating islands.
- **Tropical Coconut Trees**:
  - Naturally spawning along beaches and coasts with realistic curving trunks and swollen bases.
  - 3-stage coconut sapling growth supportable with bone meal.
  - Harvesting coconut logs yields coconut fibers, craftable into versatile **Rope**.
  - Punching or harvesting yields fresh **Coconuts**.
- **Fauna & Wildlife**:
  - **Eagle Mob**: High-altitude raptor soaring 25–40 blocks above ground that swoops down at high speed to hunt small prey (rabbits, chickens, and frogs).
  - Can be spawned via the **Eagle Spawn Egg** or naturally found around mountain peaks and ocean shores.
- **The Sky Dimension: Aetherial Aurora**:
  - A low-gravity dimension featuring floating islands surrounded by boundless open sky.
  - Unique glowing flora and blocks: **Sky Stone**, **Sky Grass**, **Glimmerstone** (Light Level 15), **Prism Wood**, and **Prism Leaves**.
  - Accessible via the **Aetherial Key** and **Aetherial Portal**.
  - Magical artifacts: **Dreamcatcher Wand** providing slow falling and levitation propulsion.

---

## 🛠️ Metallurgy & Processing Recipes

### 1. Aluminium Suite
| Source Material | Process | Output |
|---|---|---|
| `mine-origins:bauxite_ore` | Furnace / Blast Furnace | `mine-origins:aluminium` (Ingot) |
| `mine-origins:raw_bauxite` | Furnace / Blast Furnace | `mine-origins:aluminium` (Ingot) |
| `mine-origins:bauxite` (Chunk) | Furnace / Blast Furnace | `mine-origins:aluminium` (Ingot) |
| 9x `mine-origins:aluminium` | Crafting Table (3x3) | `mine-origins:aluminium_block` |
| `mine-origins:aluminium_block` | Crafting Table (1x1) | 9x `mine-origins:aluminium` |

### 2. Titanium Suite
| Source Material | Process | Output |
|---|---|---|
| `mine-origins:ilmenite_ore` | Furnace / Blast Furnace | `mine-origins:titanium` (Ingot) |
| `mine-origins:rutile_ore` | Furnace / Blast Furnace | `mine-origins:titanium` (Ingot) |
| `mine-origins:raw_titanium` | Furnace / Blast Furnace | `mine-origins:titanium` (Ingot) |
| `mine-origins:ilmenite` / `rutile` (Chunks) | Furnace / Blast Furnace | `mine-origins:titanium` (Ingot) |
| 9x `mine-origins:titanium` | Crafting Table (3x3) | `mine-origins:titanium_block` |
| `mine-origins:titanium_block` | Crafting Table (1x1) | 9x `mine-origins:titanium` |

---

## 🌴 Coconut Palm Ecosystem

- **Spawning**: Spawns automatically on beaches, coasts, and warm ocean shorelines.
- **Fiber to Rope**:
  - `9x Coconut Fiber` (obtained from logs / foliage) ➔ `mine-origins:rope`.
- **Fuel & Smelting**:
  - `mine-origins:coconut_log` ➔ `minecraft:charcoal` (in furnace).
  - `mine-origins:coconut_wood` ➔ `minecraft:charcoal` (in furnace).
- **Growth**:
  - Plant `Coconut Sprout` on sand, dirt, or grass. Apply bone meal to progress through sprout stages into a towering coconut palm.

---

## 🦅 Wildlife: Eagle

- **Behavior**:
  - Circles lazily high above mountains and coastal plains.
  - Automatically identifies rabbits, frogs, and chickens on the ground.
  - Performs rapid swoop dives to strike targets before gliding back up into orbit.
- **Breeding & Feeding**:
  - Attracted to and bred with raw chicken or rabbit.

---

## 🌌 The Sky Dimension — "Aetherial Aurora"

### Accessing the Dimension
1. Craft an **Aetherial Key** using:
   - `1x Chromatic Gem` + `2x Sky Metal Ingots`.
2. Right-click any **Quartz Block** or **Glimmerstone** with the Aetherial Key to open an **Aetherial Portal**.
3. Step inside to ascend to the floating islands. A safe landing platform is automatically generated.

### Sky Resources & Tools
- **Glimmerstone**: Naturally illuminated crystal providing maximum illumination (light level 15).
- **Prism Wood**: Ethereal purple and magenta timber craftable into **Prism Planks**.
- **Dreamcatcher Wand**:
  - Crafted with: `1x Chromatic Gem`, `1x Glimmerstone`, `1x Rope`, and `2x Sky Metal Ingots`.
  - Right-click to trigger a burst of levitation, health regeneration, and slow-falling aura.

---

## 📦 Building and Running

### Requirements
- **Java 25** (JDK 25)
- **Minecraft 26.2**
- **Fabric Loader >= 0.19.5**

### Commands
- **Compile & Validate**:
  ```bash
  ./gradlew compileJava compileClientJava
  ```
- **Build Production Mod Jar**:
  ```bash
  ./gradlew build
  ```
- **Launch Minecraft Client**:
  ```bash
  ./gradlew runClient
  ```
