"""Minimal logistics state model with action time accounting.

Locations are represented by strings. A container is loaded when its state is
``"R1"`` and otherwise stores the location where it is currently placed.
"""


class LogisticDomainWithTime:
    """Track a single robot, three containers, and cumulative action time."""

    def __init__(self):
        self.state = {
            "R1": "D1",
            "C1": "R1",
            "C2": "D1",
            "C3": "D2",
        }
        self.total_time = 0
        self.goal = {
            "C1": "D3",
            "C2": "D3",
            "C3": "D3",
        }

    def is_goal_state(self):
        """Return whether every container has reached its target location."""
        return all(
            self.state[container] == destination
            for container, destination in self.goal.items()
        )

    def move(self, destination, time_cost):
        """Move the robot to ``destination`` and account for travel time."""
        if self.state["R1"] != destination:
            print(f"Moving R1 to {destination} (Cost: {time_cost} units)")
            self.state["R1"] = destination
            self.total_time += time_cost

    def load(self, container, time_cost=1):
        """Load a container located with the robot."""
        if self.state[container] == self.state["R1"]:
            print(f"Loading {container} (Cost: {time_cost} units)")
            self.state[container] = "R1"
            self.total_time += time_cost

    def unload(self, container, location, time_cost=1):
        """Unload a container currently carried by the robot."""
        if self.state[container] == "R1":
            print(f"Unloading {container} at {location} (Cost: {time_cost} units)")
            self.state[container] = location
            self.total_time += time_cost

    def plan(self):
        """Run the supplied action sequence and report its resulting state."""
        self.move("D3", 10)
        self.unload("C1", "D3")
        self.unload("C2", "D3")
        self.unload("C3", "D3")

        print(f"Total time spent: {self.total_time}")
        if self.is_goal_state():
            print("Goal state reached!")
        else:
            print("Goal state not reached.")


if __name__ == "__main__":
    LogisticDomainWithTime().plan()
