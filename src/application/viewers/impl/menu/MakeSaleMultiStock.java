package src.application.viewers.impl.menu;

import src.application.viewers.EventMenu;
import src.domain.adapters.IMakeSaleMultiStock;
import src.domain.usecase.MakeSaleMultiStockUseCase;

public class MakeSaleMultiStock implements EventMenu {
    private final IMakeSaleMultiStock useCase = new MakeSaleMultiStockUseCase();

    @Override
    public void execute() {
        useCase.execute();
    }
}
