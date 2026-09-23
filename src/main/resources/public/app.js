const esc = (s) => (s ?? '').toString().replace(/[&<>"]/g, (c) => (
    { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;' }[c]
));

class Api {
    static async request(path, options = {}) {
        const res = await fetch(path, {
            headers: { 'Content-Type': 'application/json' },
            ...options,
        });
        if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
        return res.json();
    }

    static get(path) {
        return this.request(path);
    }

    static post(path, body) {
        return this.request(path, { method: 'POST', body: JSON.stringify(body) });
    }
}

class LibraryApp {

    constructor() {
        this.books = [];
        this.members = [];
        this.loans = [];
        this.reservations = [];
    }

    async init() {
        this.bindTabs();
        this.bindForms();
        this.bindActions();
        try {
            await this.refresh();
        } catch (err) {
            this.notify(`Failed to load data: ${err.message}`);
        }
    }

    bindTabs() {
        document.querySelectorAll('.tabs li').forEach((li) => {
            li.addEventListener('click', () => this.activateTab(li.dataset.tab, li));
        });
    }

    activateTab(tab, li) {
        document.querySelectorAll('.tabs li').forEach((t) => t.classList.remove('is-active'));
        li.classList.add('is-active');
        document.querySelectorAll('.tab-pane').forEach((p) => p.classList.add('is-hidden'));
        document.getElementById(`tab-${tab}`).classList.remove('is-hidden');
    }

    bindActions() {
        document.addEventListener('click', (e) => {
            const btn = e.target.closest('[data-action]');
            if (!btn) return;
            const { action, id } = btn.dataset;
            const run = action === 'return' ? this.returnLoan
                : action === 'fulfill' ? this.fulfill : null;
            if (run) run.call(this, id).catch((err) => this.notify(err.message));
        });
    }

    bindForms() {
        this.bindForm('book-form', (d) => Api.post('/books', {
            title: d.title, author: d.author, isbn: d.isbn, totalCopies: parseInt(d.totalCopies, 10),
        }).then(() => this.loadBooks()));

        this.bindForm('member-form', (d) => Api.post('/members', d).then(() => this.loadMembers()));

        this.bindForm('loan-form', () => {
            const memberId = document.querySelector('#loan-form [name=memberId]').value;
            const bookId = document.querySelector('#loan-form [name=bookId]').value;
            return Api.post(`/loans?memberId=${memberId}&bookId=${bookId}`)
                .then(() => Promise.all([this.loadLoans(), this.loadBooks()]));
        });

        this.bindForm('reservation-form', () => {
            const memberId = document.querySelector('#reservation-form [name=memberId]').value;
            const bookId = document.querySelector('#reservation-form [name=bookId]').value;
            return Api.post(`/reservations?memberId=${memberId}&bookId=${bookId}`)
                .then(() => this.loadReservations());
        });
    }

    bindForm(formId, handler) {
        document.getElementById(formId).addEventListener('submit', async (e) => {
            e.preventDefault();
            const form = e.target;
            const data = Object.fromEntries(new FormData(form).entries());
            try {
                await handler(data);
                form.reset();
            } catch (err) {
                this.notify(err.message);
            }
        });
    }

    async refresh() {
        await Promise.all([this.loadBooks(), this.loadMembers(), this.loadLoans(), this.loadReservations()]);
    }

    async loadBooks() {
        this.books = await Api.get('/books');
        document.getElementById('books-body').innerHTML = this.books.length
            ? this.books.map((b) => `
                <tr>
                    <td>${b.id}</td>
                    <td>${esc(b.title)}</td>
                    <td>${esc(b.author)}</td>
                    <td><span class="tag is-light">${esc(b.isbn)}</span></td>
                    <td>${b.totalCopies}</td>
                    <td>${b.availableCopies === 0
                        ? '<span class="tag is-danger is-light">Out of stock</span>'
                        : b.availableCopies}</td>
                </tr>`).join('')
            : this.emptyRow(6, 'No books yet. Add one above.');
        this.updateStats();
    }

    async loadMembers() {
        this.members = await Api.get('/members');
        document.getElementById('members-body').innerHTML = this.members.length
            ? this.members.map((m) => `
                <tr>
                    <td>${m.id}</td>
                    <td>${esc(m.name)}</td>
                    <td><a href="mailto:${esc(m.email)}">${esc(m.email)}</a></td>
                </tr>`).join('')
            : this.emptyRow(3, 'No members yet. Register one above.');
        this.updateStats();
    }

    async loadLoans() {
        this.loans = await Api.get('/loans');
        document.getElementById('loans-body').innerHTML = this.loans.length
            ? this.loans.map((l) => `
                <tr>
                    <td>${l.id}</td>
                    <td>${esc(l.book?.title)}</td>
                    <td>${esc(l.member?.name)}</td>
                    <td>${esc(l.dueDate)}</td>
                    <td>${l.returned
                        ? '<span class="tag is-success is-light">Returned</span>'
                        : l.overdue
                            ? '<span class="tag is-danger is-light">Overdue</span>'
                            : '<span class="tag is-warning is-light">On loan</span>'}</td>
                    <td>${l.overdueFee > 0
                        ? `<span class="has-text-danger has-text-weight-semibold">$${l.overdueFee.toFixed(2)}</span>`
                        : '<span class="has-text-grey">$0.00</span>'}</td>
                    <td>${l.returned ? '' : `<button class="button is-small is-success is-light" data-action="return" data-id="${l.id}"><span class="icon is-small"><i class="fas fa-check"></i></span><span>Return</span></button>`}</td>
                </tr>`).join('')
            : this.emptyRow(7, 'No loans yet.');
        this.updateStats();
    }

    async loadReservations() {
        this.reservations = await Api.get('/reservations');
        document.getElementById('reservations-body').innerHTML = this.reservations.length
            ? this.reservations.map((r) => `
                <tr>
                    <td>${r.id}</td>
                    <td>${esc(r.book?.title)}</td>
                    <td>${esc(r.member?.name)}</td>
                    <td>${esc(r.reservedAt)}</td>
                    <td>${r.fulfilled
                        ? '<span class="tag is-success is-light">Fulfilled</span>'
                        : '<span class="tag is-info is-light">Pending</span>'}</td>
                    <td>${r.fulfilled ? '' : `<button class="button is-small is-success is-light" data-action="fulfill" data-id="${r.id}"><span class="icon is-small"><i class="fas fa-check"></i></span><span>Fulfill</span></button>`}</td>
                </tr>`).join('')
            : this.emptyRow(6, 'No reservations yet.');
        this.updateStats();
    }

    async returnLoan(id) {
        await Api.post(`/loans/${id}/return`);
        await Promise.all([this.loadLoans(), this.loadBooks()]);
    }

    async fulfill(id) {
        await Api.post(`/reservations/${id}/fulfill`);
        await Promise.all([this.loadReservations(), this.loadLoans(), this.loadBooks()]);
    }

    updateStats() {
        document.getElementById('stat-books').textContent = this.books.length;
        document.getElementById('stat-members').textContent = this.members.length;
        document.getElementById('stat-loans').textContent = this.loans.filter((l) => !l.returned).length;
        document.getElementById('stat-overdue').textContent = this.loans.filter((l) => l.overdue).length;
    }

    emptyRow(colspan, message) {
        return `<tr><td colspan="${colspan}" class="has-text-centered has-text-grey">${message}</td></tr>`;
    }

    notify(message, type = 'is-danger') {
        const el = document.createElement('div');
        el.className = `notification ${type} is-light`;
        el.innerHTML = `<button class="delete"></button>${esc(message)}`;
        el.querySelector('.delete').addEventListener('click', () => el.remove());
        document.getElementById('notifications').appendChild(el);
        setTimeout(() => el.remove(), 5000);
    }
}

new LibraryApp().init();
