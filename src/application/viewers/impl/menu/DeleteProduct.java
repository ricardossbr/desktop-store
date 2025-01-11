package src.application.viewers.impl.menu;

import src.domain.adapters.IDeleteProduct;
import src.domain.usecase.DeleteProductUseCase;
import src.application.viewers.EventMenu;

public class DeleteProduct implements EventMenu {
    private final IDeleteProduct useCase = new DeleteProductUseCase();

    @Override
    public void execute() {
        useCase.execute();

    }
}
