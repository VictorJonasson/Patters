package shop;

import command.Command;
import command.CommandManager;
import strategies.Discount;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ShoppingCart {

    private ArrayList<ShoppingCartItem> items = new ArrayList<>();
    private ArrayList<Discount> discounts = new ArrayList<>();
    private CommandManager commandCollector;

    BigDecimal mostProfitableDiscount = BigDecimal.ZERO;

    public ShoppingCart(CommandManager commandCollector) {
        this.commandCollector = commandCollector;
    }

    public void addCartItem(ShoppingCartItem item) {
        items.add(item);

        Command undoCommand = new Command() {
            @Override
            public void execute() {
                items.remove(item);
            }

            @Override
            public void redo() {
                items.add(item);
            }
        };
        commandCollector.addToUndoCollector(undoCommand);

    }

    public void removeCartItem(ShoppingCartItem item) {
        items.remove(item);
        Command addCommand = new Command() {
            @Override
            public void execute() {
                items.add(item);
            }

            @Override
            public void redo() {
                items.remove(item);
            }
        };
        commandCollector.addToUndoCollector(addCommand);
    }

    public ArrayList<ShoppingCartItem> getItems() {
        return items;
    }

    public BigDecimal calculatePrice() {

        var sum = BigDecimal.ZERO;
        mostProfitableDiscount = BigDecimal.ZERO;

        for (var item : items) {
            sum = item.itemCost().multiply(BigDecimal.valueOf(item.quantity())).add(sum);
        }
        for (var discount : discounts) {

            var totalDiscount = discount.discount(items);

            if (sum.subtract(totalDiscount).intValue() > mostProfitableDiscount.intValue()) {
                mostProfitableDiscount = sum.subtract(totalDiscount);
            }
        }
        return sum.subtract(mostProfitableDiscount);
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public String receipt() {
        String line = "--------------------------------\n";
        StringBuilder sb = new StringBuilder();
        sb.append(line);
        var list = items.stream()
                .sorted(Comparator.comparing(item -> item.product().name()))
                .collect(Collectors.toList());
        for (var each : list) {
            sb.append(String.format("%-24s %7.2f \nQnt: %d%n\n", each.product().name(), each.itemCost() , each.quantity()));
        }
        sb.append(line);
        sb.append(String.format("\n%24s% 8.2f ", "Sum:", calculatePrice())+"\uD83D\uDCB2");
        sb.append(String.format("\n%24s% 8.2f", "Total Discount:", mostProfitableDiscount.multiply(BigDecimal.valueOf(-1)))+"\uD83D\uDCB2");
        return sb.toString();
    }

    public void undo() {
        int index = commandCollector.getUndoCollector().size() - 1;
        commandCollector.getUndoCollector().get(index).execute();
        commandCollector.getRedoCollector().push(commandCollector.getUndoCollector().peek());
        commandCollector.getUndoCollector().pop();
    }

    public void redo() {
        if (commandCollector.getRedoCollector().size() > 0) {
            commandCollector.getRedoCollector().get(0).redo();
        }
    }
}
