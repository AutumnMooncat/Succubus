package Succubus.actions;

import Succubus.util.Wiz;

public class FinaleAction extends DoIfAction {
    public FinaleAction(Runnable runnable) {
        super(() -> Wiz.adp().hand.isEmpty(), runnable);
    }
}
