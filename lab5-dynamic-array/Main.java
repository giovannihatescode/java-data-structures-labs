public class Main {
    public static void main(String[] args) {
        DynamicArray<String> items = new DynamicArray<>();

        // Start with an array that has room for two items.
        System.out.println("Starting capacity: " + items.capacity());

        items.add("Sword");
        items.add("Shield");

        System.out.println("Before resizing: " + items);
        System.out.println("Size: " + items.size());

        // Adding a third item forces the array to grow.
        System.out.println("\nAdding Potion...");
        items.add("Potion");

        System.out.println("After resizing: " + items);
        System.out.println("Size: " + items.size());
        System.out.println("Capacity: " + items.capacity());

        // Insert an item in the middle of the list.
        items.add(1, "Bow");
        System.out.println("\nAfter inserting Bow at index 1: " + items);

        // Read and replace an item.
        System.out.println("Item at index 2: " + items.get(2));
        System.out.println("Replaced item: " + items.set(2, "Helmet"));
        System.out.println("After replacement: " + items);

        // Remove an item and show that the list still works.
        System.out.println("Removed item: " + items.remove(0));
        System.out.println("Final list: " + items);
        System.out.println("Final size: " + items.size());
    }
}