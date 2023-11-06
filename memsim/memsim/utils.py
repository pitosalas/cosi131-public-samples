class Block:
    def __init__(self, start: int, size: int):
        self.start = start
        self.size = size

    def __str__(self):
        return f"Block: start = {pretty_mem_str(self.start)}, size = {pretty_mem_str(self.size)}"

class MemoryAllocation:
    def __init__(self, process: str, block: Block):
        self.block = block
        self.process = process

    def __str__(self):
        return f"Proc: {self.process} has: {pretty_mem_str(self.block.size)} (@: {pretty_mem_str(self.block.start)})"

def find_and_remove(lst, n) -> list[list[int]] | None:
    """
    Finds and removes the first occurrence of a consecutive sequence of n or more integers in the given list.

    Args:
    - lst: A list of integers.
    - n: An integer representing the minimum length of the consecutive sequence to be removed.

    Returns:
    A list containing two sub-lists:
    - The first sub-list contains the consecutive sequence of integers that was removed.
    - The second sub-list contains the remaining integers in the original list after the sequence was removed.
    """
    result = []
    consec = 1
    prev = lst[0] - 1

    for i, num in enumerate(lst):
        if num == prev + 1:
            result.append(num)
            consec += 1
        else:
            consec = 2
            result = [num]

        if consec > n:
            return [result, list(set(lst) - set(result))]
        prev = num
    return None

def pretty_mem_str(size: int) -> str:
    if size < 2**10:
        return f"{size:2}"
    elif size < 2**20:
        return f"{size/2**10:.1f} KB"
    elif size < 2**30:
        return f"{size/2**20:.1f} MB"
    else:
        return f"{size/2**30:.1f} GB"
    
def convert_size_with_multiplier(info: dict) -> int:
    size = info["size"]
    power_of_ten = info.get("power_of_ten")
    power_of_two = info.get("power_of_two")
    if power_of_ten is not None:
        print("po10")
        return size*10**power_of_ten
    elif power_of_two is not None:
        print("po2")
        return size*2**power_of_two
    else:
        raise Exception("Invalid convert_size_with_multiplier call.")


# Pito Code
# def flatten_free_segments(free_segments):
#     flatten_segments = []
#     start = True
#     previous = None
#     for segment in free_segments:
#         if start:
#             open = segment
#             start = False
#         elif previous is not None and segment != (previous + 1):
#             flatten_segments.append((open, previous))
#             open = segment
#         previous = segment
#     flatten_segments.append((open, free_segments[-1]))
#     return flatten_segments


def flatten_free_segments(free_segments):
    """
        Given a list of free memory segments, returns a flattened list of contiguous segments.
        chatgpt-4 improvement over my code.
        Args:
            free_segments (list): A list of integers representing free memory segments.
        Returns:
            list: A list of tuples representing contiguous memory segments.
    """
    if not free_segments:
        return []

    flattened_segments = []
    open = free_segments[0]
    previous = None

    for segment in free_segments:
        if previous is not None and segment != previous + 1:
            flattened_segments.append((open, previous))
            open = segment
        previous = segment

    flattened_segments.append((open, free_segments[-1]))
    return flattened_segments
