var users = require('../../app/controllers/users.server.controller.js'),
	passport = require('passport');

module.exports = function(app){
	app.route('/users')
		.get(users.list)
		.post(users.create);

	app.route('/users/:userId')
		.put(users.update)
		.delete(users.delete)
		.get(users.read);

	app.route('/signup')
		.get(users.renderSignup)
		.post(users.signup);

	app.route('/signin')
		.get(users.renderSignin);
		/*.post(passport.authenticate('local',{
			successRedirect:'/',
			failureRedirect:'/signin',
			failureFlash:true
		}));*/

	app.get('/signout',users.signout);

	app.param('userId',users.userById);
};
