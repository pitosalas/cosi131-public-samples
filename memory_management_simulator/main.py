"""
A simple memory magement simulation and demonstration app.
"""
import json
from memorymanager import MemoryManager


class Simulator:
    def __init__(self):
        pass

    def add_process(self, process):
        self.processes.append(process)

    def add_virtual_mapping(self, mapping):
        self.virtual_mappings.append(mapping)

    def run(self):
        self.mmanager = MemoryManager(2)
        self.mmanager.allocate_k("p1", 512)
        self.mmanager.allocate_k("p2", 1024)
        self.mmanager.allocate_k("p3", 368)
        print(self.mmanager)

    def import_json_file(self, filename):
        with open(filename, "r") as f:
            self.data = json.load(f)



    def interactive(self):
        self.mmanager = MemoryManager(2)
        while True:
            command = input("Enter command: ")
            if command == "exit":
                break
            elif command == "allocate":
                process = input(
                    "Enter process name: "
                )
                size = int(
                    input("Enter size in KB: ")
                )
                self.mmanager.allocate_k(
                    process, size
                )
            elif command == "deallocate":
                process = input(
                    "Enter process name: "
                )
                self.mmanager.deallocate(process)
            elif command == "print":
                print(self.mmanager)
            else:
                print("Invalid command")

    def batch(self):
        self.import_json_file("mm1.json")
        for step in self.data["script"]:
            print(step)


if __name__ == "__main__":
    sim = Simulator()
    #   sim.run()
    # sim.interactive()
    sim.batch()
