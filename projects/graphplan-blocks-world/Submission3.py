"""Minimal logistics-domain simulation with action costs."""


class LogisticDomainWithTime:
    """Track container locations, vehicle location, and accumulated action time.

    A container location of ``"R1"`` represents a container loaded on vehicle R1.
    """

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
        """Return whether every container is at its goal destination."""
        return all(
            self.state[container] == destination
            for container, destination in self.goal.items()
        )

    def move(self, destination, time_cost):
        """Move R1 to a destination when it is not already there."""
        if self.state["R1"] != destination:
            print(f"Moving R1 to {destination} (Cost: {time_cost} units)")
            self.state["R1"] = destination
            self.total_time += time_cost

    def load(self, container, time_cost=1):
        """Load a container that is at R1's current location."""
        if self.state[container] == self.state["R1"]:
            print(f"Loading {container} (Cost: {time_cost} units)")
            self.state[container] = "R1"
            self.total_time += time_cost

    def unload(self, container, location, time_cost=1):
        """Unload a container currently carried by R1."""
        if self.state[container] == "R1":
            print(f"Unloading {container} at {location} (Cost: {time_cost} units)")
            self.state[container] = location
            self.total_time += time_cost

    def plan(self):
        """Execute the supplied fixed action sequence and report its result."""
        self.move("D3", 10)
        self.unload("C1", "D3")
        self.unload("C2", "D3")
        self.unload("C3", "D3")

        print(f"Total time spent: {self.total_time}")
        if self.is_goal_state():
            print("Goal state reached!")
        else:
            print("Goal state not reached.")


def main():
    """Run the included logistics-domain demonstration."""
    LogisticDomainWithTime().plan()


if __name__ == "__main__":
    main()
