#!/usr/bin/env node
/**
 * A2A HelloWorld Agent Card Fetcher
 * Simple Node.js script to get agent card information
 */

const https = require('https');

// Get URL from command line argument or use default
const AGENT_URL = process.argv[2] || 'https://a2a-helloworld-1dd6ef1d53ae.herokuapp.com';

function makeRequest(path, method = 'GET', data = null) {
    return new Promise((resolve, reject) => {
        const url = new URL(AGENT_URL);
        const options = {
            hostname: url.hostname,
            port: url.port || 443,
            path: path,
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'User-Agent': 'A2A-Agent-Tester/1.0'
            }
        };

        const req = https.request(options, (res) => {
            let body = '';
            res.on('data', (chunk) => {
                body += chunk;
            });
            res.on('end', () => {
                try {
                    const jsonData = JSON.parse(body);
                    resolve(jsonData);
                } catch (e) {
                    resolve(body);
                }
            });
        });

        req.on('error', (err) => {
            reject(err);
        });

        if (data) {
            req.write(JSON.stringify(data));
        }
        req.end();
    });
}

async function getAgentCard() {
    try {
        console.log('📋 Fetching agent card...');
        const agentCard = await makeRequest('/agent/card');
        console.log('✅ Agent Card:');
        console.log(JSON.stringify(agentCard, null, 2));
        return agentCard;
    } catch (error) {
        console.error('❌ Error fetching agent card:', error.message);
        return null;
    }
}

async function getAgentHealth() {
    try {
        console.log('💚 Checking agent health...');
        const health = await makeRequest('/agent/health');
        console.log('✅ Health Status:');
        console.log(JSON.stringify(health, null, 2));
        return health;
    } catch (error) {
        console.error('❌ Error fetching agent health:', error.message);
        return null;
    }
}

async function testJsonRpcGreeting() {
    try {
        console.log('👋 Testing JSON-RPC greeting...');
        const greeting = await makeRequest('/jsonrpc', 'POST', {
            jsonrpc: '2.0',
            method: 'greeting',
            params: {},
            id: 1
        });
        console.log('✅ Greeting Response:');
        console.log(JSON.stringify(greeting, null, 2));
        return greeting;
    } catch (error) {
        console.error('❌ Error calling JSON-RPC greeting:', error.message);
        return null;
    }
}

async function main() {
    console.log('🤖 A2A HelloWorld Agent Card Fetcher');
    console.log(`🌐 Testing agent at: ${AGENT_URL}`);
    console.log('='.repeat(50));
    
    const agentCard = await getAgentCard();
    if (!agentCard) {
        process.exit(1);
    }
    
    console.log('\n' + '='.repeat(50));
    await getAgentHealth();
    
    console.log('\n' + '='.repeat(50));
    await testJsonRpcGreeting();
    
    console.log('\n✅ All tests completed successfully!');
}

main().catch(console.error);
