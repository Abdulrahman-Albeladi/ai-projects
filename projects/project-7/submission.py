"""GraphPlan solution for a four-block rearrangement problem.

This module requires the course-provided ``logic`` and ``planning`` modules.
"""

# The course scaffolding provides `planning` and `logic` modules. Import only
# the symbols we use to avoid star-import-related linter errors.
from planning import PlanningProblem, Action, GraphPlan, linearize


def blocks_world_mod_plan():
    """Construct and solve the configured Blocks World planning problem."""
    initial_state = (
        "On(C, D) & Clear(C) & OnTable(D) & " "On(A, B) & Clear(A) & OnTable(B)"
    )
    goal_state = "On(D, C) & On(C, B) & On(B, A) & OnTable(A) & Clear(D)"

    planning_problem = PlanningProblem(
        initial=initial_state,
        goals=goal_state,
        actions=[
            Action(
                "ToTable(x, y)",
                precond="On(x, y) & Clear(x)",
                effect="~On(x, y) & Clear(y) & OnTable(x)",
            ),
            Action(
                "FromTable(y, x)",
                precond="OnTable(y) & Clear(y) & Clear(x)",
                effect="~OnTable(y) & ~Clear(x) & On(y, x)",
            ),
            Action(
                "Move(x, y, z)",
                precond="On(x, y) & Clear(x) & Clear(z)",
                effect="~On(x, y) & Clear(y) & On(x, z) & ~Clear(z)",
            ),
        ],
    )

    return linearize(GraphPlan(planning_problem).execute())


if __name__ == "__main__":
    print(blocks_world_mod_plan())
