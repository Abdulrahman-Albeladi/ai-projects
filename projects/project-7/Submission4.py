"""Small logistics-domain simulation with vehicle capacity and travel time.

The default scenario represents one vehicle (``R1``) carrying ``C1`` while
``C2`` and ``C3`` remain at their initial depots. The included plan preserves
the original direct-delivery sequence; it does not claim to solve the full
delivery objective.
"""

from __future__ import annotations


class LogisticDomainWithTimeWeight:
    """Track container locations, vehicle load, and accumulated action time."""

    def __init__(self) -> None:
        self.state = {
            "R1": "D1",
            "C1": "R1",
            "C2": "D1",
            "C3": "D2",
        }
        self.weights = {"C1": 5, "C2": 8, "C3": 3}
        self.max_load = 10
        self.current_load = sum(
            self.weights[container]
            for container in self.weights
            if self.state[container] == "R1"
        )
        self.total_time = 0
        self.goal = {
            "C1": "D3",
            "C2": "D3",
            "C3": "D3",
        }

    def is_goal_state(self) -> bool:
        """Return whether every container has reached its target location."""
        return all(
            self.state[container] == self.goal[container] for container in self.goal
        )

    def move(self, destination: str, time_cost: int) -> None:
        """Move the vehicle when it is not already at the destination."""
        if self.state["R1"] != destination:
            print(f"Moving R1 to {destination} (Cost: {time_cost} units)")
            self.state["R1"] = destination
            self.total_time += time_cost

    def load(self, container: str, time_cost: int = 1) -> None:
        """Load a container if doing so does not exceed vehicle capacity."""
        container_weight = self.weights[container]
        if self.current_load + container_weight <= self.max_load:
            print(
                f"Loading {container} "
                f"(Weight: {container_weight}, Cost: {time_cost} units)"
            )
            self.state[container] = "R1"
            self.current_load += container_weight
            self.total_time += time_cost
        else:
            print(f"Cannot load {container}: Exceeds weight limit.")

    def unload(self, container: str, location: str, time_cost: int = 1) -> None:
        """Unload a container only when it is currently carried by the vehicle."""
        container_weight = self.weights[container]
        if self.state[container] == "R1":
            print(f"Unloading {container} at {location} (Cost: {time_cost} units)")
            self.state[container] = location
            self.current_load -= container_weight
            self.total_time += time_cost

    def plan(self) -> bool:
        """Execute the original direct-delivery action sequence.

        Only ``C1`` is initially loaded, so the scenario's complete goal state
        remains unmet after this sequence.
        """
        self.move("D3", 10)
        self.unload("C1", "D3")
        self.unload("C2", "D3")
        self.unload("C3", "D3")
        print(f"Total time spent: {self.total_time}")

        goal_reached = self.is_goal_state()
        if goal_reached:
            print("Goal state reached!")
        else:
            print("Goal state not reached.")
        return goal_reached


if __name__ == "__main__":
    LogisticDomainWithTimeWeight().plan()
