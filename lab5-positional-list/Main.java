public class Main {
    public static void main(String[] args) {
        LinkedPositionalList<String> itinerary =
            new LinkedPositionalList<>();

        // Save positions so we can add stops next to existing stops.
        Position<String> eiffel = itinerary.addLast("Eiffel Tower");
        Position<String> notreDame = itinerary.addLast("Notre Dame");

        System.out.println("Original itinerary:");
        for (String stop : itinerary) {
            System.out.println("- " + stop);
        }

        // Put the museum between the two original destinations.
        Position<String> museum =
            itinerary.addAfter(eiffel, "Louvre Museum");

        System.out.println("\nAdded Louvre Museum after Eiffel Tower.");

        // Add stops at the beginning and before an existing stop.
        itinerary.addFirst("Hotel");
        itinerary.addBefore(notreDame, "Lunch");

        // Update one stop and remove a temporary stop.
        itinerary.set(museum, "Louvre Museum Tour");
        Position<String> shopping = itinerary.addLast("Shopping");
        System.out.println("Removed optional stop: "
            + itinerary.remove(shopping));

        // Show that the position methods work.
        System.out.println("\nFirst stop: "
            + itinerary.first().getElement());
        System.out.println("Last stop: "
            + itinerary.last().getElement());
        System.out.println("Stop after Eiffel Tower: "
            + itinerary.after(eiffel).getElement());
        System.out.println("Stop before Notre Dame: "
            + itinerary.before(notreDame).getElement());

        // A for-each loop uses our custom iterator automatically.
        System.out.println("\nFinal itinerary using a for-each loop:");

        int number = 1;
        for (String stop : itinerary) {
            System.out.println(number + ". " + stop);
            number++;
        }

        System.out.println("Total stops: " + itinerary.size());
    }
}