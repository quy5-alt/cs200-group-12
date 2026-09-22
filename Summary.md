# 3a. Solution Summary

Our team modeled the ChocAn data-processing software described in the requirements report (Team12_Requirements_Report). We modeled only the ChocAn Data Center system itself, treating the provider terminal hardware, Acme Accounting Services' internal accounting software, and the EFT transfer mechanism as external actors or systems rather than modeling their internals — consistent with the Glossary definition that EFT here refers only to the data file produced for banks, not the transfer itself. The Use Case diagram below (Figure 1) and the Use Case descriptions in Section 3d reflect the following assumptions:

![ChocAn Use Case Diagram](Diagram%202026-09-21%2022-37-41.png)
*Figure 1: ChocAn Data Processing System Use Case Diagram*

- The Friday-midnight trigger for "Run Weekly Accounting Procedure" is time-based rather than person-initiated, so we modeled it with a "Clock (Friday Midnight)" actor in addition to Manager, since a Manager can also request the report on demand.
- "Run Weekly Accounting Procedure" includes four report/file-generation behaviors — Generate Member Reports, Generate Provider Reports, Generate Manager Summary Report, and Generate EFT File — because all four are always performed as part of that weekly run, so we modeled them with `<<include>>`, as shown in Figure 1.
- "Record Service (Bill ChocAn)" includes "Validate Member," since a provider must always validate a member before a service can be recorded. "Handle Invalid Service Code" is modeled as an `<<extend>>` of "Record Service," since an invalid service code is only encountered sometimes, not on every run of the use case.
- "Manage Member Records" and "Manage Provider Records" group the add/update/delete operations an Operator can perform on member and provider records, respectively; the individual variations (add, update, delete) are described in the Extensions of each rather than as separate diagram bubbles.
- Acme Accounting Services is external to our system but interacts with it nightly to update membership status, so we modeled this as "Update Membership Status," with Acme Accounting Services as the actor.

**Submitter:** Luong Nguyen
