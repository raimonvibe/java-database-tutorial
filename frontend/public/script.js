/**
 * Frontend JavaScript for the Simple Tutorial App
 * This file handles all the frontend logic for interacting with our Java backend
 */

const API_BASE_URL = 'http://localhost:8080/api/users';

const userForm = document.getElementById('userForm');
const usersList = document.getElementById('usersList');
const statusMessage = document.getElementById('statusMessage');
const refreshBtn = document.getElementById('refreshBtn');

/**
 * Initialize the app when the page loads
 */
document.addEventListener('DOMContentLoaded', function() {
    console.log('🚀 Frontend app initialized');
    
    loadUsers();
    
    setupEventListeners();
});

/**
 * Set up all event listeners for user interactions
 */
function setupEventListeners() {
    userForm.addEventListener('submit', handleFormSubmit);
    
    refreshBtn.addEventListener('click', loadUsers);
}

/**
 * Handle form submission to add a new user
 * @param {Event} event - The form submit event
 */
async function handleFormSubmit(event) {
    event.preventDefault();
    
    const formData = new FormData(userForm);
    const userData = {
        name: formData.get('name').trim(),
        email: formData.get('email').trim()
    };
    
    console.log('📝 Submitting user data:', userData);
    
    if (!userData.name || !userData.email) {
        showStatusMessage('Please fill in all fields', 'error');
        return;
    }
    
    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData)
        });
        
        if (response.ok) {
            const newUser = await response.json();
            console.log('✅ User created successfully:', newUser);
            
            showStatusMessage(`User "${newUser.name}" added successfully!`, 'success');
            
            userForm.reset();
            
            loadUsers();
            
        } else {
            console.error('❌ Failed to create user:', response.status);
            showStatusMessage('Failed to add user. Email might already exist.', 'error');
        }
        
    } catch (error) {
        console.error('❌ Error creating user:', error);
        showStatusMessage('Error connecting to server. Make sure the backend is running.', 'error');
    }
}

/**
 * Load and display all users from the backend
 */
async function loadUsers() {
    console.log('📋 Loading users from backend...');
    
    usersList.innerHTML = '<p class="loading">Loading users...</p>';
    
    try {
        const response = await fetch(API_BASE_URL);
        
        if (response.ok) {
            const users = await response.json();
            console.log('✅ Loaded users:', users);
            
            displayUsers(users);
            
        } else {
            console.error('❌ Failed to load users:', response.status);
            usersList.innerHTML = '<p class="error">Failed to load users from server.</p>';
        }
        
    } catch (error) {
        console.error('❌ Error loading users:', error);
        usersList.innerHTML = '<p class="error">Error connecting to server. Make sure the backend is running.</p>';
    }
}

/**
 * Display the list of users in the UI
 * @param {Array} users - Array of user objects from the backend
 */
function displayUsers(users) {
    usersList.innerHTML = '';
    
    if (users.length === 0) {
        usersList.innerHTML = '<p class="empty-state">No users found. Add some users using the form above!</p>';
        return;
    }
    
    users.forEach(user => {
        const userCard = createUserCard(user);
        usersList.appendChild(userCard);
    });
    
    console.log(`📊 Displayed ${users.length} users`);
}

/**
 * Create a user card HTML element
 * @param {Object} user - User object with id, name, and email
 * @returns {HTMLElement} - The user card element
 */
function createUserCard(user) {
    const card = document.createElement('div');
    card.className = 'user-card';
    
    const userInfo = document.createElement('div');
    userInfo.className = 'user-info';
    
    const userName = document.createElement('h3');
    userName.textContent = user.name;
    
    const userEmail = document.createElement('p');
    userEmail.textContent = user.email;
    
    userInfo.appendChild(userName);
    userInfo.appendChild(userEmail);
    
    const userIdBadge = document.createElement('span');
    userIdBadge.className = 'user-id';
    userIdBadge.textContent = `ID: ${user.id}`;
    
    const deleteBtn = document.createElement('button');
    deleteBtn.className = 'delete-btn';
    deleteBtn.textContent = '🗑️ Delete';
    deleteBtn.onclick = () => deleteUser(user.id, user.name);
    
    card.appendChild(userInfo);
    card.appendChild(userIdBadge);
    card.appendChild(deleteBtn);
    
    return card;
}

/**
 * Delete a user by ID
 * @param {number} userId - The ID of the user to delete
 * @param {string} userName - The name of the user (for confirmation)
 */
async function deleteUser(userId, userName) {
    if (!confirm(`Are you sure you want to delete user "${userName}"?`)) {
        return;
    }
    
    console.log(`🗑️ Deleting user ${userId} (${userName})`);
    
    try {
        const response = await fetch(`${API_BASE_URL}/${userId}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            console.log('✅ User deleted successfully');
            showStatusMessage(`User "${userName}" deleted successfully!`, 'success');
            
            loadUsers();
            
        } else {
            console.error('❌ Failed to delete user:', response.status);
            showStatusMessage('Failed to delete user.', 'error');
        }
        
    } catch (error) {
        console.error('❌ Error deleting user:', error);
        showStatusMessage('Error connecting to server.', 'error');
    }
}

/**
 * Show a status message to the user
 * @param {string} message - The message to display
 * @param {string} type - The type of message ('success' or 'error')
 */
function showStatusMessage(message, type) {
    statusMessage.textContent = message;
    statusMessage.className = `status-message ${type}`;
    
    setTimeout(() => {
        statusMessage.style.display = 'none';
    }, 5000);
}
