**Generate Summary Report**



**Use Case:** Generate Summary Report

**Context**: Produces the weekly accounts-payable summary for the ChocAn manager, listing every provider paid that week, their consultation counts and fees, and overall totals.

**Actors**: ChocAn Manager, Scheduler (system-triggered at midnight Friday)



**Main Success Scenario:**



1. Trigger occurs: the weekly accounting procedure runs automatically at midnight Friday.
2. System reads the week's file of services provided.
3. System groups the service records by provider.
4. For each provider, system computes the number of consultations and the total fee owed for the week.
5. System writes one summary line per provider: provider name, provider number, number of consultations, and total fee.
6. System computes overall totals: total number of providers paid, total number of consultations, and the overall fee total for the week.
7. System appends the overall totals to the end of the report.
8. System writes the completed report to a file for the manager.
9. System confirms the report was generated.



**Extensions**:

1a. Manager requests the report manually during the week (instead of waiting for the Friday trigger): system proceeds from step 2 using services recorded so far that week.

3a. No services were recorded for the week: system generates a report showing zero providers, zero consultations, and a $0.00 total, and proceeds to step 8.

8a. Report file cannot be written (e.g., disk/storage error): system displays an error to whoever triggered the report and does not mark the report as generated; scheduled runs log the failure for retry, and manager-requested runs return the operator to the reporting menu.

