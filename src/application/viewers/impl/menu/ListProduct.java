package src.application.viewers.impl.menu;

import src.application.viewers.EventMenu;
import src.domain.adapters.IListProduct;
import src.domain.usecase.ListProductsUseCase;

public class ListProduct implements EventMenu {
    private final IListProduct useCase = new ListProductsUseCase();

    @Override
    public void execute() {
        useCase.execute();
    }


}
