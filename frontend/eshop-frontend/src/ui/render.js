// src/ui/render.js

import { selectViewState } from "../infra/store/selectors.js";
import { createHandlers } from "../app/actionHandlers/createHandlers.js";
import { navigationPanelHandlers } from "../app/actionHandlers/navigationPanelHandlers.js";

import { LoadingView } from "./views/LoadingView.js";
import { ErrorView } from "./views/ErrorView.js";

import { DashboardView } from "./views/DashboardView.js";
import { AuthenticationView } from "./views/AuthenticationView.js";

import { ProductListView } from "./views/ProductListView.js";
import { ProductDetailView } from "./views/ProductDetailView.js";
import { CartView } from "./views/CartView.js";
import { OrderHistoryView } from "./views/OrderHistoryView.js";

import { NavigationPanelComponent } from "./components/NavigationPanelComponent.js";

export function render(root, state, dispatch) {
  root.replaceChildren();

  const viewState = selectViewState(state);
  const handlers = createHandlers(dispatch, viewState);

  let view;

  if (viewState.capabilities) {
    const navHandlers =
      navigationPanelHandlers(dispatch, viewState);

    const navigationPanel =
      NavigationPanelComponent({
        handlers: navHandlers,
      });

    root.appendChild(navigationPanel);
  }

  switch (viewState.type) {
    case "LOADING":
      view = LoadingView();
      break;

    case "ERROR":
      view = ErrorView({
        message: viewState.message,
        handlers,
      });
      break;

    case "DASHBOARD":
      view = DashboardView({
        viewState,
        handlers,
      });
      break;

    case "AUTHENTICATION":
      view = AuthenticationView({
        viewState,
        handlers,
      });
      break;

    case "PRODUCT_LIST":
      view = ProductListView({
        viewState,
        handlers,
      });
      break;

    case "PRODUCT_DETAIL":
      if (!viewState.product) {
        view = ErrorView({
          message: "Produkt nebyl nalezen.",
          handlers,
        });
      } else {
        view = ProductDetailView({
          viewState,
          handlers,
        });
      }
      break;

    case "CART":
      view = CartView({
        viewState,
        handlers,
      });
      break;

    case "ORDER_HISTORY":
      view = OrderHistoryView({
        viewState,
        handlers,
      });
      break;

    default:
      view = document.createTextNode(
        `Unknown view type: ${viewState.type}`
      );
  }

  root.appendChild(view);

  const { notification } = state.ui;

  if (notification) {
    const notificationElement =
      document.createElement("div");

    notificationElement.textContent =
      notification.message;

    notificationElement.classList.add("notification");

    root.appendChild(notificationElement);
  }
}