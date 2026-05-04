CREATE OR REPLACE FUNCTION public.update_invoices_totals() 
RETURNS TRIGGER AS $$
BEGIN
    UPDATE invoices i 
    SET subtotal = COALESCE(sub.sum_total, 0), 
        total_amount = COALESCE(sub.sum_total, 0) + i.tax_amount
    FROM (
        SELECT  
            inv_ids.invoice_id,
            SUM(ii.line_total) AS sum_total
        FROM (
            SELECT invoice_id FROM new_table
            UNION
            SELECT invoice_id FROM old_table
        ) inv_ids
        LEFT JOIN invoice_items ii 
            ON ii.invoice_id = inv_ids.invoice_id
        GROUP BY inv_ids.invoice_id
    ) sub
    WHERE i.invoice_id = sub.invoice_id;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_invoice_totals_insert
AFTER INSERT ON invoice_items
REFERENCING NEW TABLE AS new_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_invoices_totals();

CREATE TRIGGER trg_update_invoice_totals_update
AFTER UPDATE ON invoice_items
REFERENCING NEW TABLE AS new_table OLD TABLE AS old_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_invoices_totals();

CREATE TRIGGER trg_update_invoice_totals_delete
AFTER DELETE ON invoice_items
REFERENCING OLD TABLE AS old_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_invoices_totals();