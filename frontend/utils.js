/**
 * FitFuel Frontend JavaScript Utilities
 * Provides reusable functions for notifications, modals, forms, and API calls
 */

// ====== NOTIFICATION SYSTEM ======

class NotificationManager {
    constructor() {
        this.notifications = [];
    }

    show(message, type = 'info', duration = 3000) {
        const notification = document.createElement('div');
        notification.className = `notification ${type}`;
        notification.textContent = message;
        document.body.appendChild(notification);

        if (duration > 0) {
            setTimeout(() => {
                notification.classList.add('hide');
                setTimeout(() => notification.remove(), 300);
            }, duration);
        }

        return notification;
    }

    success(message, duration) {
        return this.show(message, 'success', duration);
    }

    error(message, duration) {
        return this.show(message, 'error', duration);
    }

    warning(message, duration) {
        return this.show(message, 'warning', duration);
    }

    info(message, duration) {
        return this.show(message, 'info', duration);
    }
}

const notify = new NotificationManager();

// ====== MODAL MANAGER ======

class ModalManager {
    constructor() {
        this.modals = new Map();
    }

    create(id, title, content, options = {}) {
        const overlay = document.createElement('div');
        overlay.className = 'modal-overlay';
        overlay.id = `overlay-${id}`;

        const dialog = document.createElement('div');
        dialog.className = 'modal-dialog';
        dialog.innerHTML = `
            <div class="modal-header">
                <h2 class="modal-title">${title}</h2>
                <button class="modal-close" onclick="modal.close('${id}')">&times;</button>
            </div>
            <div class="modal-body">${content}</div>
        `;

        overlay.appendChild(dialog);
        document.body.appendChild(overlay);

        overlay.addEventListener('click', (e) => {
            if (e.target === overlay && options.closeOnOverlay !== false) {
                this.close(id);
            }
        });

        this.modals.set(id, overlay);
        return overlay;
    }

    open(id) {
        const modal = this.modals.get(id);
        if (modal) modal.classList.add('active');
    }

    close(id) {
        const modal = this.modals.get(id);
        if (modal) modal.classList.remove('active');
    }

    remove(id) {
        const modal = this.modals.get(id);
        if (modal) {
            modal.remove();
            this.modals.delete(id);
        }
    }
}

const modal = new ModalManager();

// ====== FORM VALIDATOR ======

class FormValidator {
    static validate(formElement) {
        const inputs = formElement.querySelectorAll('.form-input');
        let isValid = true;

        inputs.forEach(input => {
            if (!this.validateField(input)) {
                isValid = false;
            }
        });

        return isValid;
    }

    static validateField(input) {
        const value = input.value.trim();
        let isValid = true;

        // Clear previous error
        input.classList.remove('error', 'success');

        // Check required
        if (input.hasAttribute('required') && !value) {
            this.showError(input, 'This field is required');
            isValid = false;
        }

        // Check email
        else if (input.type === 'email' && value) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(value)) {
                this.showError(input, 'Please enter a valid email');
                isValid = false;
            }
        }

        // Check password strength
        else if (input.type === 'password' && value) {
            if (value.length < 8) {
                this.showError(input, 'Password must be at least 8 characters');
                isValid = false;
            } else if (!/[A-Z]/.test(value) || !/[0-9]/.test(value)) {
                this.showError(input, 'Password must contain uppercase and numbers');
                isValid = false;
            }
        }

        // Check number range
        else if (input.type === 'number') {
            const min = parseFloat(input.getAttribute('min'));
            const max = parseFloat(input.getAttribute('max'));
            const num = parseFloat(value);

            if (isNaN(num) || (min && num < min) || (max && num > max)) {
                this.showError(input, `Please enter a valid number between ${min} and ${max}`);
                isValid = false;
            }
        }

        if (isValid) {
            input.classList.add('success');
        }

        return isValid;
    }

    static showError(input, message) {
        input.classList.add('error');
        const errorElement = input.nextElementSibling;
        if (errorElement && errorElement.classList.contains('form-error')) {
            errorElement.textContent = message;
        }
    }
}

// ====== API CLIENT ======

class ApiClient {
    constructor(baseUrl = 'http://localhost:8080/api') {
        this.baseUrl = baseUrl;
        this.token = localStorage.getItem('authToken');
    }

    setToken(token) {
        this.token = token;
        localStorage.setItem('authToken', token);
    }

    getHeaders() {
        const headers = { 'Content-Type': 'application/json' };
        if (this.token) {
            headers['Authorization'] = `Bearer ${this.token}`;
        }
        return headers;
    }

    async request(endpoint, options = {}) {
        const url = `${this.baseUrl}${endpoint}`;
        const config = {
            ...options,
            headers: { ...this.getHeaders(), ...options.headers }
        };

        try {
            const response = await fetch(url, config);

            if (!response.ok) {
                if (response.status === 401) {
                    // Token expired or invalid
                    localStorage.removeItem('authToken');
                    window.location.href = 'index.html';
                }
                const error = await response.json();
                throw new Error(error.message || `HTTP ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            notify.error(`Error: ${error.message}`);
            throw error;
        }
    }

    async get(endpoint) {
        return this.request(endpoint, { method: 'GET' });
    }

    async post(endpoint, data) {
        return this.request(endpoint, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    }

    async put(endpoint, data) {
        return this.request(endpoint, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    }

    async delete(endpoint) {
        return this.request(endpoint, { method: 'DELETE' });
    }
}

const api = new ApiClient();

// ====== THEME MANAGER ======

class ThemeManager {
    constructor() {
        this.isDark = localStorage.getItem('theme') === 'dark';
        this.apply();
    }

    toggle() {
        this.isDark = !this.isDark;
        this.apply();
    }

    apply() {
        if (this.isDark) {
            document.body.classList.add('dark');
        } else {
            document.body.classList.remove('dark');
        }
        localStorage.setItem('theme', this.isDark ? 'dark' : 'light');
        this.updateThemeButton();
    }

    updateThemeButton() {
        const btn = document.getElementById('themeToggle');
        if (btn) {
            btn.textContent = this.isDark ? '☀️' : '🌙';
        }
    }
}

const theme = new ThemeManager();

// ====== STORAGE MANAGER ======

class StorageManager {
    static set(key, value) {
        localStorage.setItem(key, JSON.stringify(value));
    }

    static get(key) {
        const item = localStorage.getItem(key);
        return item ? JSON.parse(item) : null;
    }

    static remove(key) {
        localStorage.removeItem(key);
    }

    static clear() {
        localStorage.clear();
    }

    // Specific methods for app data
    static saveUser(user) {
        this.set('currentUser', user);
    }

    static getUser() {
        return this.get('currentUser');
    }

    static saveMeals(meals) {
        this.set('meals', meals);
    }

    static getMeals() {
        return this.get('meals') || [];
    }

    static addMeal(meal) {
        const meals = this.getMeals();
        meals.push(meal);
        this.saveMeals(meals);
    }

    static updateMeal(id, updates) {
        const meals = this.getMeals();
        const index = meals.findIndex(m => m.id === id);
        if (index !== -1) {
            meals[index] = { ...meals[index], ...updates };
            this.saveMeals(meals);
        }
    }

    static deleteMeal(id) {
        const meals = this.getMeals().filter(m => m.id !== id);
        this.saveMeals(meals);
    }
}

// ====== FORMATTING UTILITIES ======

class Formatter {
    static formatCalories(calories) {
        return Math.round(calories).toLocaleString();
    }

    static formatDate(date) {
        if (typeof date === 'string') {
            date = new Date(date);
        }
        return date.toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });
    }

    static formatTime(time) {
        if (typeof time === 'string') {
            const [hours, minutes] = time.split(':');
            const hour = parseInt(hours);
            const ampm = hour >= 12 ? 'PM' : 'AM';
            const displayHour = hour % 12 || 12;
            return `${displayHour}:${minutes} ${ampm}`;
        }
        return time;
    }

    static formatDuration(minutes) {
        if (minutes < 60) return `${minutes}m`;
        const hours = Math.floor(minutes / 60);
        const mins = minutes % 60;
        return mins > 0 ? `${hours}h ${mins}m` : `${hours}h`;
    }

    static formatPercentage(value, total) {
        return Math.round((value / total) * 100);
    }

    static formatMacro(macroName, value) {
        return `${macroName}: ${value}g`;
    }
}

// ====== MATH UTILITIES ======

class MathHelper {
    static calculateBMR(age, weight, height, gender) {
        // Harris-Benedict Formula
        if (gender.toUpperCase() === 'MALE') {
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }

    static calculateTDEE(bmr, activityLevel) {
        const activityMultipliers = {
            'SEDENTARY': 1.2,
            'LIGHTLY_ACTIVE': 1.375,
            'MODERATELY_ACTIVE': 1.55,
            'VERY_ACTIVE': 1.725,
            'EXTREMELY_ACTIVE': 1.9
        };
        const multiplier = activityMultipliers[activityLevel] || 1.5;
        return bmr * multiplier;
    }

    static calculateCalorieGoal(tdee, goal) {
        switch (goal.toUpperCase()) {
            case 'WEIGHT_LOSS':
                return tdee - 500; // 0.5kg per week loss
            case 'WEIGHT_GAIN':
                return tdee + 500; // 0.5kg per week gain
            case 'MAINTENANCE':
            default:
                return tdee;
        }
    }

    static calculateBMI(weight, height) {
        // height in cm, convert to meters
        const heightMeters = height / 100;
        return (weight / (heightMeters * heightMeters)).toFixed(1);
    }

    static getBMICategory(bmi) {
        if (bmi < 18.5) return 'Underweight';
        if (bmi < 25) return 'Normal weight';
        if (bmi < 30) return 'Overweight';
        return 'Obese';
    }
}

// ====== CHART UTILITIES ======

class ChartHelper {
    static createProgressBar(element, current, total, animated = true) {
        const percentage = Math.min(100, (current / total) * 100);
        element.style.width = animated ? '0%' : percentage + '%';

        if (animated) {
            setTimeout(() => {
                element.style.transition = 'width 1s ease';
                element.style.width = percentage + '%';
            }, 100);
        }

        element.textContent = Math.round(percentage) + '%';
    }

    static createRadialProgress(element, current, total) {
        const percentage = (current / total) * 100;
        const circumference = 2 * Math.PI * 45;
        const offset = circumference - (percentage / 100) * circumference;

        element.style.strokeDashoffset = offset;
    }
}

// ====== DOM UTILITIES ======

class DOMHelper {
    static createElement(tag, options = {}) {
        const element = document.createElement(tag);

        if (options.className) element.className = options.className;
        if (options.id) element.id = options.id;
        if (options.text) element.textContent = options.text;
        if (options.html) element.innerHTML = options.html;
        if (options.attributes) {
            Object.entries(options.attributes).forEach(([key, value]) => {
                element.setAttribute(key, value);
            });
        }

        return element;
    }

    static bindEvents(element, events) {
        Object.entries(events).forEach(([event, handler]) => {
            element.addEventListener(event, handler);
        });
    }

    static toggleClass(element, className) {
        element.classList.toggle(className);
    }

    static addClass(element, className) {
        element.classList.add(className);
    }

    static removeClass(element, className) {
        element.classList.remove(className);
    }

    static hasClass(element, className) {
        return element.classList.contains(className);
    }

    static show(element) {
        element.classList.remove('hidden');
    }

    static hide(element) {
        element.classList.add('hidden');
    }

    static isVisible(element) {
        return !this.hasClass(element, 'hidden');
    }
}

// ====== ANIMATION UTILITIES ======

class AnimationHelper {
    static fadeIn(element, duration = 300) {
        element.style.opacity = '0';
        element.style.display = 'block';
        setTimeout(() => {
            element.style.transition = `opacity ${duration}ms ease`;
            element.style.opacity = '1';
        }, 10);
    }

    static fadeOut(element, duration = 300) {
        element.style.transition = `opacity ${duration}ms ease`;
        element.style.opacity = '0';
        setTimeout(() => {
            element.style.display = 'none';
        }, duration);
    }

    static slideDown(element, duration = 300) {
        element.style.maxHeight = '0';
        element.style.overflow = 'hidden';
        setTimeout(() => {
            element.style.transition = `max-height ${duration}ms ease`;
            element.style.maxHeight = element.scrollHeight + 'px';
        }, 10);
    }

    static slideUp(element, duration = 300) {
        element.style.transition = `max-height ${duration}ms ease`;
        element.style.maxHeight = '0';
    }
}

// ====== EXPORT FOR MODULE USE ======

if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        NotificationManager,
        ModalManager,
        FormValidator,
        ApiClient,
        ThemeManager,
        StorageManager,
        Formatter,
        MathHelper,
        ChartHelper,
        DOMHelper,
        AnimationHelper
    };
}
