using System;
using System.Threading.Tasks;
using InstagramServer.IServices;
using InstagramServer.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Data.SqlClient;

namespace InstagramServer.Controllers
{
    [Route("[controller]")]
    public class UserController : Controller
    {
        private readonly IUserService _userService;

        public UserController(IUserService userService)
        {
            _userService = userService;
        }

        [HttpGet("{id}", Name = "GetUser")]
        public async Task<IActionResult> GetById(Guid id)
        {
            var user = await _userService.GetUserById(id);
            if (user == null)
            {
                return BadRequest();
            }

            return Ok(user);
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] User user)
        {
            if (user == null)
            {
                return NotFound();
            }

            try
            {
                var addedUser = await _userService.Add(user);
                return CreatedAtRoute("GetUser", new {id = addedUser.UserId}, addedUser);
            }
            catch (SqlException sqlExc)
            {
                return BadRequest("Incorrect username");
            }
            catch (Exception ex)
            {
                return BadRequest("Incorrect username");
            }
            
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(Guid id)
        {
            var user = await _userService.GetUserById(id);
            if (user == null)
            {
                return NotFound();
            }

            await _userService.DeleteUser(user);
            return NoContent();
        }

        [HttpGet("username={username}&password={password}")]
        public async Task<IActionResult> Credentials(string username, string password)
        {
            var user = await _userService.CheckCredentials(username, password);
            if (user == null)
            {
                return NotFound();
            }

            return Ok(user);
        }
    }
}