CREATE OR REPLACE FUNCTION public.update_totals()
RETURNS TRIGGER AS $$
BEGIN
    NEW.total_amount := COALESCE(NEW.subtotal, 0) + COALESCE(NEW.tax_amount, 0);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tgr_update_invoice_totals_on_invoices
BEFORE INSERT OR UPDATE OF tax_amount, subtotal ON invoices
FOR EACH ROW
EXECUTE FUNCTION public.update_totals();

CREATE TRIGGER tgr_update_order_totals_on_orders
BEFORE INSERT OR UPDATE OF tax_amount, subtotal ON orders
FOR EACH ROW
EXECUTE FUNCTION public.update_totals();