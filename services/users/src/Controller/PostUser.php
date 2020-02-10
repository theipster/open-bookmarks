<?php

declare(strict_types=1);

namespace App\Controller;

use App\Entity\User;
use Doctrine\DBAL\Exception\UniqueConstraintViolationException;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;
use Symfony\Component\HttpKernel\Exception\HttpExceptionInterface;
use Symfony\Component\HttpKernel\Exception\UnprocessableEntityHttpException;
use Symfony\Component\Routing\Annotation\Route;

class PostUser extends AbstractController
{
    /**
     * @todo Consider symfony/validator
     *
     * @throws BadRequestHttpException
     */
    private function createUserFromRequest(Request $request): User
    {
        // Validation
        if (!$request->request->has('username')) {
            throw new BadRequestHttpException('Username not provided.');
        }

        // Create entity
        return User::builder()
            ->generateUuid()
            ->username($request->request->get('username'))
            ->build();
    }

    /**
     * @Route("/users", methods={"POST"})
     */
    public function handle(Request $request, EntityManagerInterface $entityManager): Response
    {
        try {
            // Create user
            $user = $this->createUserFromRequest($request);

            // Persist user
            $this->persistUser($user, $entityManager);

            // Return HTTP 201 with location.
            $response = new JsonResponse();
            $response->headers->set('Location', '/users/' . $user->getUuid());
            return $response->setStatusCode(201);

        } catch (HttpExceptionInterface $httpException) {
            return $this->handleHttpException($httpException);
        }
    }

    private function handleHttpException(HttpExceptionInterface $httpException): Response
    {
        return new JsonResponse(
            $httpException->getMessage(),
            $httpException->getStatusCode(),
            $httpException->getHeaders()
        );
    }

    /**
     * @todo Consider symfony/psr-http-message-bridge
     *
     * @throws UnprocessableEntityHttpException
     */
    private function persistUser(User $user, EntityManagerInterface $entityManager): void
    {
        $entityManager->persist($user);
        try {
            $entityManager->flush();
        } catch (UniqueConstraintViolationException $exception) {
            throw new UnprocessableEntityHttpException('Username already taken.');
        }
    }
}
