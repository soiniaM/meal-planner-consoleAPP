import ui.ConsoleMenu;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    System.out.println("=================================");
    System.out.println("       MEAL PLANNER APP");
    System.out.println("=================================");

    System.out.println();
    System.out.println("Welcome to the Meal Planner!");
    System.out.println();
    System.out.println();
    System.out.println("1. Manage Recipes");
    System.out.println("2. Create Weekly Meal Plan");
    System.out.println("3. View Shopping List");
    System.out.println("4. Track Grocery Expenses");
    System.out.println("5. View Weekly Summary");
    System.out.println("0. Exit ");
    System.out.println("Choose an option:");
    Scanner scanner = new Scanner(System.in);
    int inputChoice = scanner.nextInt();
    ConsoleMenu.menuChoice(inputChoice);


}
