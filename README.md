# AI Projects: Classical Planning and Prolog Knowledge Systems


Symbolic reasoning and planning exercises in Prolog and Python, focused on logic, search, and classical AI foundations.

Technologies: Python · Prolog · Automated Planning

## Highlights

- Logic-programming exercises for symbolic knowledge representation (Prolog).
- Planning-oriented modeling in Python, including a Blocks World GraphPlan example and a small logistics simulation.
- Small, inspectable projects with clear state/action representations and goal checks.

## Projects

| Project | Location |
|---|---|
| Prolog Knowledge Systems | [projects/prolog-knowledge-systems](projects/prolog-knowledge-systems) |
| GraphPlan Blocks World (plus a small logistics simulation) | [projects/graphplan-blocks-world](projects/graphplan-blocks-world) |

## Requirements

- Python 3.x
- SWI-Prolog (recommended) for the Prolog projects: https://www.swi-prolog.org/
- No external Python dependencies are required for the logistics simulation script.
- The Blocks World GraphPlan example depends on external modules named `logic` and `planning` (see details below); these are not included here.

## How to run

### Prolog Knowledge Systems

Directory: [projects/prolog-knowledge-systems](projects/prolog-knowledge-systems)

Files:
- part_1.pl — apartment logic puzzle constraints and queries
- part_2.pl — small library ontology and circulation model (with recursion/tabling and dynamic facts)

Steps (using SWI-Prolog):
1) Launch SWI-Prolog (for example, `swipl`).
2) Load a file, e.g.:
   - `?- [projects/prolog-knowledge-systems/part_1].`
   - `?- [projects/prolog-knowledge-systems/part_2].`
3) Try queries (examples; these show usage but do not claim specific outputs here):
   - In part_1.pl: `?- pig_tenant(Tenant).`  `?- frisbee_player(Player).`
   - In part_2.pl: `?- initialize_languages.` then `?- high_order_member_of('The Hobbit', items).` or `?- next_item('The Hobbit', Suggested).`

Notes:
- part_2.pl includes a comment noting that sample catalog titles were generated with ChatGPT; that attribution is retained in the source.

### GraphPlan Blocks World (Python)

Entry point: [projects/graphplan-blocks-world/blocks_world_graphplan.py](projects/graphplan-blocks-world/blocks_world_graphplan.py)

About dependencies:
- This example expects external modules named `logic` and `planning` that define types/functions such as `Action`, `PlanningProblem`, `GraphPlan`, and `linearize`.
- These modules are course-provided in some curricula and are NOT redistributed here.

Run (when those modules are available on your PYTHONPATH):
- `python projects/graphplan-blocks-world/blocks_world_graphplan.py`

If you don’t have the course modules:
- You can still review the modeling approach in the source file.
- To experiment, you may adapt the problem specification to a public planning library. Two commonly referenced options are:
  - AIMA Python (planning components): https://github.com/aimacode/aima-python
  - pyperplan (classical planning): https://github.com/aibasel/pyperplan
- These are not drop-in replacements; some refactoring is typically required to match APIs.

Expected behavior (for context only): when the required modules are present, running the script constructs a Blocks World planning problem and prints a linearized plan.

### Logistics simulation: time + capacity + weights (Python)

Entry point: [projects/graphplan-blocks-world/logistics_time_weight.py](projects/graphplan-blocks-world/logistics_time_weight.py)

- Self-contained; no third-party packages required.
- Models a single vehicle with a weight limit and simple travel time accounting.
- Includes a direct-delivery action sequence primarily to illustrate state updates, capacity checks, and goal evaluation.

Run:
- `python projects/graphplan-blocks-world/logistics_time_weight.py`

What you’ll see:
- Printed actions (moves/loads/unloads), accumulated time, and whether the configured goal state was reached. This script is illustrative and not an optimal solver.

## External dependencies and attribution

- Blocks World GraphPlan depends on non-redistributed course-style modules named `logic` and `planning`. If you have legal access to such modules, place them on your PYTHONPATH to run the example. Otherwise, consider adapting the problem to a public planning library (links above).
- Prolog part_2.pl retains a source comment attributing sample catalog titles to ChatGPT; that transparency is intentional.
- No third-party course code is included in this repository.

## License

Use and redistribution are governed by the repository’s [LICENSE](LICENSE).

## Notes for reviewers

- The focus here is on clear state/action modeling, constraints, and goal checks—skills that transfer to production systems even when tooling shifts from symbolic to statistical methods.
- If a specific public GraphPlan-compatible library is desired, I can add a minimal adapter in a follow-up, keeping third-party code out of the repo.
