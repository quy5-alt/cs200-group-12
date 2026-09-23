**Use case:** Generate Provider Reports

**Context:** Produces a weekly report for every provider who performed at least one billable service during the reporting week, listing each service rendered and the total amount owed. This use case is included by "Run Weekly Accounting Procedure" and runs as part of the Friday-midnight batch cycle.

**Actors:** ChocAn Data Processing System (invoked internally as part of Run Weekly Accounting Procedure)

**Main Success Scenario:**

1. System begins provider report generation as part of the weekly accounting run.
2. System retrieves all service records with a date/time received falling within the current billing week.
3. System groups the retrieved service records by provider number.
4. For each provider with at least one service record this week:
    1. System looks up the provider's name, street address, city, state, and ZIP code from the provider master record.
    2. System lists each service line for that provider: date of service, date/time received by computer, member name, member number, service code, and fee to be paid.
    3. System computes the total number of consultations and the total fee for the week for that provider.
5. System formats the provider's report with header (provider info), the service lines, and the footer summary (total consultations, total fee).
6. System sends the completed report to the provider (print/mail).
7. Use case ends once every provider with service activity this week has a report generated.

**Extensions:**

- **2a.** No service records exist for the current week: System produces no provider reports and logs that the week had zero billed services; the use case ends.
- **4a.** A provider had no service records this week: System skips that provider — no report is generated for them.
- **4.1a.** A service record references a provider number not found in the master file: System logs a data-integrity error, excludes that record from any report, and continues with the next record.
- **6a.** The report cannot be delivered (e.g., printing/mailing failure): System logs the delivery failure for that provider and continues generating reports for the remaining providers.
